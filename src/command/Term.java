package command;

import database.course.CourseDb;
import database.course.CourseDbImpl;
import database.course.student.CourseStudentDbImpl;
import interfaces.Command;
import interfaces.Enrollment;
import interfaces.SystemManager;
import system.Course;
import system.Instructor;
import system.Student;

import java.util.ArrayList;
import java.util.List;

public class Term implements Command {
	
	private String year;
	private String semester = Course.FALL;
	private final String command;
	private List<Student> studentList;
	private SystemManager manager;
	private CourseDb courseDb;

	public Term(String command,String year, SystemManager manager) {
		//default semester is fall
		this.year = year;
		this.command = command;
		this.studentList = manager.getStudents();
		this.manager = manager;
		this.courseDb = new CourseDbImpl();
	}

	public Term(String command,String semester,String year, SystemManager manager) {
		this.year = year;
		this.semester = semester;
		this.command = command;
		this.studentList = manager.getStudents();
		this.manager = manager;
		this.courseDb = new CourseDbImpl();
	}

	@Override
	public String execute() {
		System.out.println(this.command);
		return "> begin,"+this.semester+","+this.year;
	}
	
	public String getNextTerm() {
		List<Course> courses = courseDb.getAllCoursesByTerm(semester,year);
		for(Course course: courses) {
			handleIncompleteGrade(course.getID(),course.getStudentEnrolledList(semester,year), semester,year);
		}

		if(semester.equals(Course.FALL)) {
			semester = Course.SPRING;
		}else if(semester.equals(Course.SPRING)) {
			semester = Course.SUMMER;
		}else {
			semester = Course.FALL;
			int curr = Integer.parseInt(year) +1 ;
			year = Integer.toString(curr);
			reRegisterCourses(year);
		}

		return semester;
	}

	private void reRegisterCourses(String year) {
		List<Course> allCourses = courseDb.getAllCourses();
		for(Course course: allCourses) {
			boolean isInsert =  false;
			Course newRegistrationCourse = new Course(course.getID(),course.getCourseTitle(),null);
			List<Course.Term> semesterList = course.getSemester();
			for(Course.Term sem : semesterList) {
				if(!sem.semester.equals(Course.NONE)) {
					newRegistrationCourse.addTerm(sem.semester,year);
					isInsert = true;
				}
			}
			if(isInsert) {
				courseDb.insertCourse(newRegistrationCourse);
			}
    	}
	}

	public void handleIncompleteGrade(String courseID, List<String> studentEnrolledList, String semester,String year) {
		for(String studentID : studentEnrolledList) {
			final Student student = manager.getStudent(studentID);
			final List<Course.Term> terms = new ArrayList<>();
			terms.add(new Course.Term(semester,year));
			final List<CourseStudentDbImpl.Record> courseCompleted = student.getCompletedCourseByTerm(terms);
			boolean update = true;
			for(CourseStudentDbImpl.Record record : courseCompleted)
				if(record.courseID.equals(courseID)) {
					update = false;
					break;
				}
			if(update) {
				student.addCourseCompleted(courseID, "I",semester,year,Enrollment.ENROLLED.getValue());
			}
		}
	}

	public String getYear() {
		return this.year;
	}
	
	public String getSemester(){
		return this.semester;
	}
	
	
	public void addInstructor(String instructorID, String courseID,int capacity) {
		Course course = courseDb.getCourse(courseID);
		course.addInstructor(instructorID, getSemester(),getYear());
		course.setCapacity(instructorID,capacity, getSemester(),getYear());
	}
	
	public Instructor getInstructor(String id) {
		return manager.getInstructor(id);
	}
	
	public List<Course> getInstructorCourses(String instructorID) {
		return manager.getInstructorTeachingCourses(instructorID,semester,year);
	}
	public int getCapacity(String courseID){
		Course course = courseDb.getCourse(courseID);
		return course.getTotalCapacity(semester,getYear());
	}
	
	public int getSeatAvailable(String courseID){
		Course course = courseDb.getCourse(courseID);;
		return course.getSeatAvailable(semester,getYear());
	}
	
	public String getStudentName(String studentID) {
		Student student = getStudent(studentID);
		return student.getName();
	}
	
	public Enrollment enroll(String studentID, String courseID) {
		Course course = courseDb.getCourse(courseID);
		Student student = getStudent(studentID);
		return course.enroll(student, semester,getYear());
		
	}

	public String recordGrade(String studentID, String courseID, String grade) {
		return manager.assignGrade(studentID,courseID,grade,this.semester,year);
	}

	public List<CourseStudentDbImpl.Record> getStudentRecord(String studentID) {
		Student student = getStudent(studentID);
		return student.getRecordAllTerms();
	}

	public String getCourseTitle(String courseID) {
		return manager.getCourseTitle(courseID);
	}
	
	private Student getStudent(String id) {
		for(Student student: studentList) {
			if(student.getID().equals(id)) {
				return student;
			}
		}
		return null;
	}
}
