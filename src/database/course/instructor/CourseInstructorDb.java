package database.course.instructor;

import system.Instructor;

import java.util.List;

public interface CourseInstructorDb {
	void createCourseInstructorTable();
	List<Instructor> getAllInstructorTeachingCourses(String semester,String year);
	List<String> getCourseIdInstructorTeachingPerTerm(String instructorID,String semester, String year);

	int getTotalCapcity(String courseID, String semester,String year);
	int getCapacity(String courseID, String instructorID, String semester, String year);

	void updateCapacity(String courseID, String instructorID, String semester,String year, int newCapacity);

	void insertInstructor(String instructorID, String courseID, String semester,String year, int initialCapacity);
}
