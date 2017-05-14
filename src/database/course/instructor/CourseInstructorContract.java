package database.course.instructor;

import database.DbContract;
import database.course.CourseContract;

public class CourseInstructorContract {

	public static final String TABLE_COURSE_INSTRUCOTR_NAME = DbContract.DB_NAME+".COURSE_INSTRUCTOR ";

	public static final String ID ="id";
	public static final String SEMESTER ="semester";
	public static final String CAPACITY ="course_capacity";
	public static final String INSTRUCTOR_UDID ="instructor_udid";
	public static String YEAR ="year";



	static final String INSERT_COURSE_INSTRUCTOR = "INSERT INTO "
			+ CourseInstructorContract.TABLE_COURSE_INSTRUCOTR_NAME +" ("
			+ CourseInstructorContract.ID +" ,"
			+ CourseInstructorContract.INSTRUCTOR_UDID +" ,"
			+ CourseInstructorContract.CAPACITY +" ,"
			+ CourseInstructorContract.SEMESTER +" ,"
			+ CourseInstructorContract.YEAR

			+ " )"+
			" VALUES ('%s' ,'%s', '%s','%s','%s')";


	static final String SELECT_COURSES_CAPACITY_BY_ID = "SELECT * FROM "+ CourseInstructorContract.TABLE_COURSE_INSTRUCOTR_NAME+" WHERE "+
			CourseInstructorContract.ID+" = '%s' AND "+CourseInstructorContract.SEMESTER +" ='%s' AND "+CourseContract.YEAR+ " = '%s'";

	static final String SELECT_COURSES_INSTRUCTOR_BY_TERM = "SELECT * FROM "+ CourseInstructorContract.TABLE_COURSE_INSTRUCOTR_NAME+" WHERE "+
			CourseInstructorContract.SEMESTER+" = '%s' AND "+CourseContract.YEAR+ " = '%s'";

	static final String UPDATE_INSTRUCTOR_CAPACITY_BY_ID = "UPDATE "+ CourseInstructorContract.TABLE_COURSE_INSTRUCOTR_NAME
			+" SET " +CourseInstructorContract.CAPACITY +" ='%s' WHERE  "
			+CourseInstructorContract.ID+" = "+"'%s' AND "+CourseInstructorContract.INSTRUCTOR_UDID+" = '%s'"+
			" AND "+CourseInstructorContract.SEMESTER +" ='%s' AND "+CourseContract.YEAR+ " = '%s'";


	static final String SELECT_COURSES_CAPACITY_BY_ID_INSTRUCTOR = "SELECT * FROM "+ CourseInstructorContract.TABLE_COURSE_INSTRUCOTR_NAME+" WHERE "+
			CourseInstructorContract.ID+" = '%s' AND "+CourseInstructorContract.SEMESTER +" ='%s' AND "+ CourseContract.YEAR+ " = '%s'" +
			" AND "+CourseInstructorContract.INSTRUCTOR_UDID +" ='%s'";

	static String SELECT_INSTRUCTOR_COURSES_BY_TERM = "SELECT * FROM "+ CourseInstructorContract.TABLE_COURSE_INSTRUCOTR_NAME+" WHERE "+
			CourseInstructorContract.INSTRUCTOR_UDID+" = '%s' AND "+CourseInstructorContract.SEMESTER +" ='%s' AND "+ CourseContract.YEAR+ " = '%s'";
}
	
	
	
	
