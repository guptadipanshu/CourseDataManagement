package system;


import database.course.student.CourseStudentDb;
import database.course.student.CourseStudentDbImpl;
import interfaces.Enrollment;

import java.util.List;

/**
 * Student class to keep track for information about a student
 * @author dipanshugupta
 *
 */
public class Student extends Person{
	
	private final CourseStudentDb courseDb;
	
	public Student(final String id, final String name, final String address, final String number){
		super(id,name,address,number);
		this.courseDb = new CourseStudentDbImpl();
	}
	
	/**
	 * gets the unique id associated with a student
	 * @return the UDID associated with a student
	 */
	public String getID() {
		return super.getID();
	}
	/**
	 * Get the name of the student
	 * @return the name of the student
	 */
	public String getName(){
		return super.getName();
	}
	
	public void addCourseCompleted(String courseID, String grade,String semester, String year,String enrollmentStatus){
		courseDb.addCourseGrade(getID(),courseID,grade,semester,year,enrollmentStatus);
	}
	
	public List<CourseStudentDbImpl.Record> getRecordAllTerms(){
		return courseDb.getRecordAllTerms(getID());
	}

	public List<CourseStudentDbImpl.Record> getRecordByTerm(String semester, String year){
		return courseDb.getGradesByTerm(getID(),semester,year);
	}

	public List<CourseStudentDbImpl.Record> getCompletedCourse() {
		return courseDb.getCompletedCourse(getID());
	}

	public List<CourseStudentDbImpl.Record> getCompletedCourseByTerm(List<Course.Term> terms) {
		return courseDb.getCompletedCourseByTerm(getID(),terms);
	}

	public List<Course> getWaitListedCourse(String semester, String year) {
		return courseDb.getWaitListedCourse(semester,year, Enrollment.NO_SEATS.getValue());
	}
}
