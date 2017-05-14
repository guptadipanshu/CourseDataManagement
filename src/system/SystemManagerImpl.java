package system;

import command.*;
import dataanalysis.DataAnalysisFacade;
import dataanalysis.WekaDataAnalysisFacade;
import database.DbHelper;
import database.course.CourseDb;
import database.course.CourseDbImpl;
import database.course.instructor.CourseInstructorDb;
import database.course.instructor.CourseInstructorDbImpl;
import database.course.student.CourseStudentDbImpl;
import database.instructor.InstructorDb;
import database.instructor.InstructorDbImpl;
import database.student.StudentDb;
import database.student.StudentDbImpl;
import interfaces.Command;
import interfaces.Enrollment;
import interfaces.SystemManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SystemManagerImpl implements SystemManager {

	private final List<Command> commandList;
	private int numberOfSessions;
	private final StudentDb studentDb;
	private final CourseDb courseDb;
	private final InstructorDb instructorDb;
	private String year = "2016";

	public SystemManagerImpl() {
		commandList = new LinkedList<>();
		numberOfSessions = 0;
		//below is for database implementation
		DbHelper.getInstance().startJDBCConnection();
		studentDb = new StudentDbImpl();
		courseDb = new CourseDbImpl();
		instructorDb = new InstructorDbImpl();

		instructorDb.createInstructorDb();
		studentDb.createStudentTable();
		courseDb.createCourseTable();
	}

	@Override
	public void loadCourses(final File courseFile, final File termFile) {
		final Map<Integer,Course> courseMap = new HashMap<>();
		final List<String> courseDetails = getDetails(courseFile);
		for(String str: courseDetails) {
			String[] courses = str.split(",");
			Course course = new Course(courses[0],courses[1],null);
			courseMap.put(Integer.parseInt(course.getID()),course);
		}

		List<String> termDetails = getDetails(termFile);
		List<Integer> offeredCourses = new ArrayList<>();
		for(String str: termDetails) {
			String[] terms = str.split(",");
			int courseID = Integer.parseInt(terms[0]);
			offeredCourses.add(courseID);
			Course course = courseMap.get(courseID);
			if(course != null) {
				course.addTerm(terms[1], year);
				courseDb.insertCourse(course);
			}
		}
		for(Integer courseID: courseMap.keySet()) {
			//add courses that are not registered to the db
			if(!offeredCourses.contains(courseID)) {
				Course course = courseMap.get(courseID);
				course.addTerm(Course.NONE, year);
				courseDb.insertCourse(course);
			}
		}
	}

	@Override
	public void loadStudents(final File file) {
		List<String> studentDetails = getDetails(file);
		for(String str: studentDetails) {
			String[] stu = str.split(",");
			Student student = new Student(stu[0],stu[1],stu[2],stu[3]);
			studentDb.insertStudent(student);
		}
	}

	@Override
	public void loadInstructors(final File file) {
		List<String> instructoreDetails = getDetails(file);
		for(String str: instructoreDetails) {
			String[] inst = str.split(",");
			Instructor instructor = new Instructor(inst[0],inst[1],inst[2],inst[3]);
			instructorDb.insertInstructor(instructor);
		}
	}

	private List<String> getDetails(final File file) {
		List<String> parsedList = new ArrayList<>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				parsedList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return parsedList;
	}

	@Override
	public int getStudentCount() {
		return studentDb.getAllStudent().size();
	}

	@Override
	public int getInstructorCount() {
		return instructorDb.getAllInstructors().size();
	}

	@Override
	public int getStudentAsInstructorCount() {
		int count = 0;
		List<Student> studentIDSet = studentDb.getAllStudent();
		List<Instructor> instructors = instructorDb.getAllInstructors();
		for(Student student : studentIDSet) {
			for(Instructor instructor : instructors) {
				if (instructor.getID().equals(student.getID())){
					count++;
					break;
				}
			}
		}
		return count;
	}

	@Override
	public List<Instructor> getInstructors() {
		return instructorDb.getAllInstructors();
	}

	@Override
	public List<String> getCoursesDetail() {
		List<String> courseDetail = new ArrayList<>();
		List<Course> courses = courseDb.getAllCourses();
		for(Course course : courses) {
			String detail = course.getID()+","+course.getCourseTitle();
			List<Course.Term> semesters = course.getSemester();
			for(Course.Term sem : semesters) {
				if(sem != null && sem.semester != "") {
					detail += ","+sem;
				}
			}
			courseDetail.add(detail);
		}
		return courseDetail;
	}

	@Override
	public Course getCourse(String courseID) {
		return courseDb.getCourse(courseID);
	}

	@Override
	public void loadPreRequisites(File preReqFile) {
		List<String> courseDetails = getDetails(preReqFile);
		for(String str: courseDetails) {
			String[] courses = str.split(",");
			String courseID = courses[1];
			String preReq = courses[0];
			Course course = courseDb.getCourse(courseID);
			course.addPrerequiste(preReq);
		}
	}
	@Override
	public void loadCommand(File file) {
		List<String> commands = getDetails(file);
		for(String command: commands) {
			String [] instructions = command.split(",");
			addCommand(command, instructions);
		}
	}

	private void addCommand(String command, String[] instructions) {
		switch(instructions[0]) {
			case "start_sim" : this.commandList.add(new Term(command,instructions[1],this));
				this.year = instructions[1];
				numberOfSessions++;
				break;
			case "next_term" : this.commandList.add(new NextTerm(command,getStartTerm()));
				break;
			case "stop_sim" : this.commandList.add(new StopTerm(command,getStartTerm()));
				break;
			case "allocate_seats" : this.commandList.add(new AllocateSeat(command,instructions[1],instructions[2],instructions[3],getStartTerm()));
				break;
			case "assign_grade" : this.commandList.add(new AssignGrade(command,instructions[1],instructions[2],instructions[3],getStartTerm()));
				break;
			case "request_course" : this.commandList.add(new RequestCourse(command,instructions[1],instructions[2],getStartTerm()));
				break;
			case "instructor_report" :this.commandList.add(new InstructorRecord(command,instructions[1],getStartTerm()));
				break;
			case "student_report" : this.commandList.add(new StudentRecord(command,instructions[1],getStartTerm()));
				break;
			default :
				System.out.println("> error");
		}
	}

	private Term getStartTerm() {
		int sessionSoFar = 0;
		for(Command command : this.commandList) {
			if(command instanceof Term) {
				sessionSoFar ++;
				if(sessionSoFar == numberOfSessions) {
					return (Term)command;
				}
			}
		}
		return null;
	}

	@Override
	public List<Command> getCommands() {
		return this.commandList;
	}

	@Override
	public List<Student> getStudents() {
		return studentDb.getAllStudent();
	}

	@Override
	public Instructor getInstructor(String id) {
		return instructorDb.getInstructor(id);
	}

	@Override
	public List<Course> getInstructorTeachingCourses(String instructorId, String semester, String year) {
		List<Course> courses = new ArrayList<>();
		CourseInstructorDb courseInstructorDb = new CourseInstructorDbImpl();
		List<String>  courseID = courseInstructorDb.getCourseIdInstructorTeachingPerTerm(instructorId,semester,year);
		for(String id : courseID) {
			courses.add(getCourse(id));
		}
		return courses;
	}

	public List<Instructor> getAllInstructors(String semester, String year) {
		CourseInstructorDb courseInstructorDb = new CourseInstructorDbImpl();
		return courseInstructorDb.getAllInstructorTeachingCourses(semester,year);
	}

	@Override
	public int getInstructorCourseCapacity(String instructorId, String courseId, String semester, String year) {
		Course course = getCourse(courseId);
		return course.getCapacity(instructorId,semester,year);
	}

	@Override
	public int getCourseCapacity(String courseId, String semester, String year) {
		Course course = getCourse(courseId);
		return course.getTotalCapacity(semester,year);
	}



	@Override
	public void setStartTerm(String year) {
		commandList.clear();
		Term start_term = new Term("start_term", Course.FALL, year, this);
		commandList.add(start_term);
	}

	@Override
	public String getCurrentYear() {
		Term startTerm = getStartTerm();
		if(startTerm == null) return null;
		return startTerm.getYear();
	}

	@Override
	public String getCurrentSemester() {
		Term startTerm = getStartTerm();
		if(startTerm == null) return null;
		return startTerm.getSemester();
	}

	@Override
	public void startNextTerm() {
		Term startTerm = getStartTerm();
		NextTerm nextTerm = new NextTerm("next_term",startTerm);
		nextTerm.execute();
	}

	@Override
	public void addStudent(String studentId, String name, String address, String number) {
		Student  student = new Student(studentId,name,address,number);
		studentDb.insertStudent(student);
	}

	@Override
	public void addInstructor(String instructorId, String name, String address, String number) {
		Instructor  instructor = new Instructor(instructorId,name,address,number);
		instructorDb.insertInstructor(instructor);
	}

	@Override
	public void addCourse(String courseId, String title, List<Course.Term> terms) {
		Course course = new Course(courseId,title,terms);
		courseDb.insertCourse(course);
	}

	@Override
	public boolean addPreRequiste(String courseId, String preRequisteCourseId) {
		Course course = getCourse(courseId);
		return course.addPrerequiste(preRequisteCourseId);
	}

	@Override
	public boolean removePreRequiste(String courseId, String preRequisteCourseId) {
		Course course = getCourse(courseId);
		return course.removePreRequiste(preRequisteCourseId);
	}

	@Override
	public List<Course> getAllCourses() {
		return courseDb.getAllCourses();
	}

	@Override
	public String getCourseTitle(String courseID) {
		return courseDb.getCourse(courseID).getCourseTitle();
	}

	@Override
	public Student getStudent(String studentId) {
		return studentDb.getStudent(studentId);
	}

	@Override
	public List<CourseStudentDbImpl.Record> getStudentRecord(String studentId) {
		Student student = studentDb.getStudent(studentId);
		return student.getRecordAllTerms();
	}

	@Override
	public List<CourseStudentDbImpl.Record> getStudentRecordByTerm(String studentId, String semester, String year) {
		Student student = studentDb.getStudent(studentId);
		return student.getRecordByTerm(semester,year);
	}


	@Override
	public List<Course> getWaitListedCourse(String studentId, String courseID, String semester, String year) {
		Student student = studentDb.getStudent(studentId);
		return student.getWaitListedCourse(semester,year);
	}

	@Override
	public String assignGrade(String studentID, String courseID, String grade, String semester, String year) {
		Student student = studentDb.getStudent(studentID);
		student.addCourseCompleted(courseID, grade,semester,year, Enrollment.ENROLLED.getValue());
		return "> recorded,"+grade;
	}

	@Override
	public int getCoursesCount() {
		return courseDb.getAllCourses().size();
	}

	@Override
	public List<Course> getPreRequisteCourse(String courseId) {
		Course course = courseDb.getCourse(courseId);
		List<String> preReq = course.getPreRequsisteCourseId();
		List<Course> courses = new ArrayList<>();
		for(String id: preReq) {
			courses.add(courseDb.getCourse(id));
		}
		return courses;
	}

	@Override
	public boolean updateStudent(String studentId, String name, String address, String number) {
		Student student = getStudent(studentId);
		if(student != null) {
			return studentDb.updateStudent(studentId,name,address,number);
		}
		return false;
	}

	@Override
	public boolean updateInstructor(String instructorId, String name, String address, String number) {
		Instructor instructor = getInstructor(instructorId);
		if(instructor != null) {
			return instructorDb.updateInstructor(instructorId,name,address,number);
		}
		return false;
	}

	@Override
	public boolean updateCourse(String courseId, String title) {
		Course course = getCourse(courseId);
		if(course != null) {
			return courseDb.updateCourse(courseId,title);
		}
		return false;
	}

	@Override
	public String getInstructorReport(String instructorId, String semester, String year) {
		List<Command> commands = new ArrayList<>();
		Term term = new Term("start_term",semester,year,this);
		commands.add((new InstructorRecord("instructor_report",instructorId,term)));
		String result ="";
		for(Command command: commands) {
			result = result.concat(command.execute());
		}
		return result;
	}

	@Override
	public String getStudentReportAllTerms(String studentId) {
		List<Command> commands = new ArrayList<>();
		commands.add((new StudentRecord("student_report",studentId,getStartTerm())));
		String result ="";
		for(Command command: commands) {
			result = result.concat(command.execute());
		}
		return result;
	}

	@Override
	public String allocateCourse(String courseId, String instructorId, int capacity, String semester, String year) {
		List<Command> commands = new ArrayList<>();
		Term term = new Term("start_term",semester,year,this);
		commands.add((new AllocateSeat("request_course",instructorId,courseId,String.valueOf(capacity),term)));
		String result ="";
		for(Command command: commands) {
			result = result.concat(command.execute());
		}
		return result;
	}

	@Override
	public String requestCourse(String studentId, String courseID, String semester, String year) {
		List<Command> commands = new ArrayList<>();
		Term term = new Term("start_term",semester,year,this);
		commands.add((new RequestCourse("request_course",studentId,courseID,term)));
		String result ="";
		for(Command command: commands) {
			result = result.concat(command.execute());
		}
		return result;
	}

	@Override
	public String executeDataAnalysis() {
		DataAnalysisFacade dataAnalysisFacade = new WekaDataAnalysisFacade();
		dataAnalysisFacade.prepareModel();
		return dataAnalysisFacade.executeModelAnalysis();
	}

}
