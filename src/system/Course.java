package system;

import database.course.CourseDb;
import database.course.CourseDbImpl;
import database.course.instructor.CourseInstructorDb;
import database.course.instructor.CourseInstructorDbImpl;
import database.course.prerequiste.CoursePreReqDb;
import database.course.prerequiste.CoursePreReqDbImpl;
import database.course.student.CourseStudentDb;
import database.course.student.CourseStudentDbImpl;
import interfaces.Enrollment;

import java.util.*;

/**
 * Class to store course information
 *
 * @author dipanshugupta
 *
 */
public class Course {

	public static final String FALL = "Fall";
	public static final String SPRING = "Spring";
	public static final String SUMMER = "Summer";
	public static final String NONE = "Not Offered";

	private final String courseID;
	private final String courseTitle;
	private final List<Term> semester;

	private CourseStudentDb courseStudentDb;
	private CourseInstructorDb courseInstructorDb;
	private CoursePreReqDb coursePreReqDb;
	private CourseDb courseDb;

	public Course(final String courseID, final String courseTitle, final List<Term> semester) {
		this.courseID = courseID;
		this.courseTitle = courseTitle;

		courseDb = new CourseDbImpl();
		courseInstructorDb = new CourseInstructorDbImpl();
		courseStudentDb = new CourseStudentDbImpl();
		coursePreReqDb = new CoursePreReqDbImpl();

		courseInstructorDb.createCourseInstructorTable();
		courseStudentDb.createCourseStudentTable();
		coursePreReqDb.createCoursePreReqTable();

		if (semester != null) {
			this.semester = semester;
		} else {
			this.semester = new ArrayList<>(3);
		}
	}

	/**
	 * Get the UDID associated with the course
	 *
	 * @return UDID
	 */
	public String getID() {
		return this.courseID;
	}

	/**
	 * register the course with the semesters
	 *
	 * @param semester
	 *            FALL, SPRING, SUMMER
	 */
	public void addTerm(final String semester, String startYear) {
		switch (semester) {
			case FALL:
				this.semester.add(new Term(semester,startYear));
				break;
			case SPRING:
				this.semester.add(new Term(semester,startYear));
				break;
			case SUMMER:
				this.semester.add(new Term(semester,startYear));
				break;
			default:
				this.semester.add(new Term(semester,""));
		}

	}

	/**
	 * get the list of all the semester the course is available
	 *
	 * @return list of semester
	 */
	public List<Term> getSemester() {
		return this.semester;
	}

	/**
	 * get the title of the course
	 *
	 * @return course title
	 */
	public String getCourseTitle() {
		return this.courseTitle;
	}

	public List<String> getPreRequsisteCourseId() {
		return coursePreReqDb.getPrerequiste(this.courseID);
	}

	public int getSeatAvailable(String term,String year) {
		return courseDb.getSeatsAvailable(this.courseID,term,year);
	}

	public Enrollment enroll(Student student,String semester,String year) {
		final Enrollment enrollment;
		final int seatAvailable = getSeatAvailable(semester,year);
		if (!checkPrerequistes(student)) {
			enrollment = Enrollment.MISSING_PREREQ;
		}else if (seatAvailable > 0) {
			courseDb.updateSeatAvailable(courseID,semester,year,seatAvailable-1);
			enrollment = Enrollment.ENROLLED;
		}else {
			enrollment = Enrollment.NO_SEATS;
		}

		courseStudentDb.setEnrollmentStatus(courseID,student.getID(),enrollment.getValue(),semester,year);
		return enrollment;
	}

	private boolean checkPrerequistes(Student student) {
		final List<CourseStudentDbImpl.Record> courseCompleted = student.getCompletedCourse();
		final List<String> preRequsiste = getPreRequsisteCourseId();

		if(preRequsiste.size() > 0 && courseCompleted.size() == 0) {
			return false;
		}

		for (String preCourseID : preRequsiste) {
			for (CourseStudentDbImpl.Record record : courseCompleted) {
				if (!record.courseID.equals(preCourseID)) {
					return false;
				} else {
					final String grade = record.grade;
					if (!grade.equals("A") && !grade.equals("B") && !grade.equals("C") && !grade.equals("D")) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public int getTotalCapacity(String term,String year) {
		return courseInstructorDb.getTotalCapcity(courseID,term,year);
	}

	public int getCapacity(String instructorID, String semester,String year) {
		return courseInstructorDb.getCapacity(courseID,instructorID,semester,year);
	}

	public int setCapacity(String instructorID, final int newCapacity, String semester,String year) {
		int total = getCapacity(instructorID,semester,year);
		int seatAvailable = getSeatAvailable(semester,year);
		if (newCapacity > total) {
			courseInstructorDb.updateCapacity(courseID,instructorID,semester,year,newCapacity);
			int extra = newCapacity - total;
			seatAvailable += extra;
			courseDb.updateSeatAvailable(courseID,semester,year,seatAvailable);
		} else if (seatAvailable > 0 && newCapacity < total) {
			int capacityBefore = getTotalCapacity(semester,year);
			int seatsUsed = capacityBefore - seatAvailable;
			courseInstructorDb.updateCapacity(courseID, instructorID,semester,year,newCapacity);
			int capacityAfter = getTotalCapacity(semester,year);
			if (capacityAfter < seatsUsed) {
				int capacity = newCapacity + (seatsUsed - capacityAfter);
				courseInstructorDb.updateCapacity(courseID, instructorID,semester,year,capacity);
				capacityAfter = getTotalCapacity(semester,year);
			}
			seatAvailable = seatAvailable - (capacityBefore - capacityAfter);
			if (seatAvailable < 0) {
				seatAvailable = 0;
			}
			courseDb.updateSeatAvailable(courseID,semester,year,seatAvailable);
		}
		return getTotalCapacity(semester,year);
	}

	public void addInstructor(String instructorID, String semester, String year) {
		courseInstructorDb.insertInstructor(instructorID,courseID,semester,year,0);
	}

	public List<String> getStudentEnrolledList(String semester, String year) {
		return courseStudentDb.getEnrolledStudentsByTerm(courseID,semester,year);
	}

	public boolean addPrerequiste(String preReq) {
		if(addingPreReqValid(preReq)) {
			coursePreReqDb.addPrerequiste(courseID, preReq);
			return true;
		}else {
			return false;
		}
	}



	public boolean removePreRequiste(String preRequisteCourseId) {
		List<String> preReq = coursePreReqDb.getPrerequiste(courseID);
		if(preReq.contains(preRequisteCourseId)) {
			coursePreReqDb.removePreReq(courseID, preRequisteCourseId);
			return true;
		}
		return false;
	}

	private boolean addingPreReqValid(String preReq) {
		List<String> currentCourse = coursePreReqDb.getPrerequiste(courseID);
		currentCourse.add(preReq);
		//create bfs map
		Map<String,List<String>> preReqMap = new HashMap<>();
		preReqMap.put(courseID,currentCourse);
		List<Course> courses = courseDb.getAllCourses();
		for(Course course : courses) {
			String id = course.getID();
			if(!id.equals(courseID)) {
				preReqMap.put(id,coursePreReqDb.getPrerequiste(id));
			}
		}
		Queue<String> q = new LinkedList<>();
		List<String> visited = new ArrayList<>();
		q.add(courseID);
		visited.add(courseID);
		while (!q.isEmpty()) {
			String id = q.remove();
			List<String> neighbors = preReqMap.get(id);
			for(String adjacent : neighbors) {
				if(visited.contains(adjacent)) {
					return false;
				}else {
					visited.add(adjacent);
					q.add(adjacent);
				}
			}
		}
		return true;
	}

	public static class Term {
		public final String semester;
		public final String year;
		public  Term(String semester,String year) {
			this.semester = semester;
			this.year = year;
		}
	}
}
