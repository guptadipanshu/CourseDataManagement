package database.course.student;


import system.Course;

import java.util.List;

public interface CourseStudentDb {
	void createCourseStudentTable();
	void addCourseGrade(String studentID,String courseID, String grade, String semester,String year, String enrollmentStatus);
	List<CourseStudentDbImpl.Record> getRecordAllTerms(String studentID);
	List<CourseStudentDbImpl.Record> getCompletedCourseByTerm(String studentId, List<Course.Term> term);
	List<CourseStudentDbImpl.Record> getCompletedCourse(String studentId);

	void setEnrollmentStatus(String courseID, String studentId, String enrollmentStatus, String semester,String year);

	List<String> getEnrolledStudentsByTerm(String courseID, String semester,String year);

	List<CourseStudentDbImpl.Record> getGradesByTerm(String id, String semester,String year);

	List<Course> getWaitListedCourse(String semester, String year, String enrollmentStatus);
}
