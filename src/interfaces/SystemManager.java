package interfaces;

import java.io.File;
import java.util.List;
import java.util.Map;

import database.course.student.CourseStudentDbImpl;
import system.Course;
import system.Instructor;
import system.Student;
/**
 * Interface to interact with the university course management system
 * @author dipanshugupta
 *
 */
public interface SystemManager {

	/*********************Start Student API's ******************/
	public List<Student> getStudents();

	public int getStudentCount();

	public Student getStudent(String studentId);

	public List<CourseStudentDbImpl.Record> getStudentRecord(String studentId);

	public List<CourseStudentDbImpl.Record> getStudentRecordByTerm(String studentId,String semester,String year);

	public String requestCourse(String studentId, String courseID, String semester, String year);

	public List<Course> getWaitListedCourse(String studentId, String courseID, String semester, String year);

	/****************End Student API******************/


	/************Start Instructor API's********************/

	public int getInstructorCount();

	public int getStudentAsInstructorCount();

	public List<Instructor> getInstructors();

	public List<Instructor> getAllInstructors(String semester, String year);

	public Instructor getInstructor(String instructorId);

	public List<Course> getInstructorTeachingCourses(String instructorId,String semester,String year);

	public int getInstructorCourseCapacity(String instructorId,String courseId,String semester,String year);

	public int getCourseCapacity(String courseId,String semester,String year);

	public String allocateCourse(String courseId, String instructorId, int capacity ,String semester, String year);

	public String assignGrade(String studentID, String courseID, String grade, String semester, String year);

	/************End Instructor API******************/

	/***********Start Admin API**************************/
	public void setStartTerm(String year);

	public String getCurrentYear();

	public String getCurrentSemester();

	public void startNextTerm();

	public void addStudent(String studentId,String name, String address, String number);

	public void addInstructor(String instructorId,String name, String address, String number);

	public void addCourse(String courseId, String title, List<Course.Term> terms);

	public boolean addPreRequiste(String courseId, String preRequisteCourseId);

	public boolean removePreRequiste(String courseId, String preRequisteCourseId);

	public List<Course> getAllCourses();

	public List<String> getCoursesDetail();

	public Course getCourse(String courseID);

	public String getCourseTitle(String courseID);

	public int getCoursesCount();

	public List<Course> getPreRequisteCourse(String courseId);

	public boolean updateStudent(String studentId,String name, String address, String number);

	public boolean updateInstructor(String instructorId,String name, String address, String number);

	public boolean updateCourse(String courseId,String title);

	public String getInstructorReport(String instructorId, String semester, String year);

	public String getStudentReportAllTerms(String studentId);
	/**********End Admin API*********************/

	/**
	 * read the courses from a CSV file
	 * @param courseFile valid csv file to read data from
	 * @param termFile valid .csv file to read the semseters courses are available
	 */
	public void loadCourses(final File courseFile, final File termFile);

	/**
	 * read the student details from a CSV file
	 * @param file valid students .csv file
	 */
	public void loadStudents(final File file);

	/**
	 * read the instructor details from a CSV file
	 * @param file valid instructor .csv file
	 */
	public void loadInstructors(final File file);

	public void loadPreRequisites(final File file);

	public void loadCommand(final File file);

	public List<Command> getCommands();

	public String executeDataAnalysis();

}
