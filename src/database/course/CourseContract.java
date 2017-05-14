package database.course;

import database.DbContract;
import database.course.instructor.CourseInstructorContract;
import database.student.StudentContract;

public class CourseContract {
	public static final String TABLE_COURSE_NAME = DbContract.DB_NAME+".COURSE ";

	public static final String ID ="id";
	public static final String TITLE ="title";
	public static final String SEMESTER ="semester";
	public static final String SEAT_AVAILABLE ="seats_available";
	public static final String YEAR = "year";


	static final String INSERT_COURSE = "INSERT INTO "
			+ CourseContract.TABLE_COURSE_NAME +" ("
			+ CourseContract.ID +" ,"
			+ CourseContract.TITLE +" ,"
			+ CourseContract.SEAT_AVAILABLE +" ,"
			+ CourseContract.SEMESTER +" ,"
			+ CourseContract.YEAR
			+ " )"+
			" VALUES ('%s' ,'%s', '%s','%s','%s')";


	static final String SELECT_COURSE_BY_ID = "SELECT * FROM "+ CourseContract.TABLE_COURSE_NAME+" WHERE "+
			CourseContract.ID+" = '%s'";


	public static final String SELECT_ALL_COURSE = "SELECT * FROM "+ CourseContract.TABLE_COURSE_NAME;

	static final String SELECT_ALL_COURSE_BY_TERM = "SELECT * FROM " + CourseContract.TABLE_COURSE_NAME + " WHERE " +
			CourseContract.SEMESTER + " = '%s' AND "+CourseContract.YEAR+ " = '%s'";

	static final String UPDATE_SEATS_AVAILABLE = "UPDATE "+ CourseContract.TABLE_COURSE_NAME
			+" SET " +CourseContract.SEAT_AVAILABLE +" ='%s' WHERE  "
			+CourseContract.ID+" = "+"'%s' AND "+CourseInstructorContract.SEMESTER +" ='%s' AND "+CourseContract.YEAR+ " = '%s'";

	static final String UPDATE_COURSE = "UPDATE "+ CourseContract.TABLE_COURSE_NAME
			+" SET " +CourseContract.TITLE +" ='%s'"+
			" WHERE  " +CourseContract.ID+" = "+"'%s'";

	static final String GET_COURSE_SEATS_BY_TERM = "SELECT * FROM " + CourseContract.TABLE_COURSE_NAME + " WHERE " +
			CourseContract.ID + " = '%s' AND " +CourseContract.SEMESTER + " = '%s' AND "+CourseContract.YEAR+ " = '%s'";
}
	
	
	
	
