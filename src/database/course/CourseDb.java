package database.course;

import java.util.List;
import system.Course;

public interface CourseDb {
	void createCourseTable();
	void insertCourse(Course course);
	void insertCourseByInstructor(String courseId,String semester,String year);
	Course getCourse(String courseID);
	List<Course> getAllCourses();

    int getSeatsAvailable(String courseID, String semseter,String year);

	void updateSeatAvailable(String courseID, String semseter,String year, int seatCapacity);

    List<Course> getAllCoursesByTerm(String semester,String year);

    boolean updateCourse(String courseId, String title);
}
