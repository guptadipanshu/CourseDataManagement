package database.course.student;

import database.DbContract;
import database.course.CourseContract;
import database.course.instructor.CourseInstructorContract;
import interfaces.Enrollment;

public class CourseStudentContract {

	public static final String TABLE_COURSE_STUDENT_NAME = DbContract.DB_NAME+".COURSE_STUDENT ";


	public static final String ID ="id";
	public static final String SEMESTER ="semester";
	public static final String STUDENT_UDID ="student_udid";
	public static final String COURSE_GRADE ="course_grade";
	public static final String COURSE_ENROLLMENT_STATUS ="course_enrollment";
	public static String YEAR ="year";

	public static final String SELECT_COURSE_STUDENT_ENROLLMENTS_FORANALYSIS = "SELECT student_udid, id as taken FROM " + CourseStudentContract.TABLE_COURSE_STUDENT_NAME;

	static final String INSERT_COURSE_STUDENT = "INSERT INTO "
			+ CourseStudentContract.TABLE_COURSE_STUDENT_NAME +" ("
			+ CourseStudentContract.ID +" ,"
			+ CourseStudentContract.STUDENT_UDID +" ,"
			+ CourseStudentContract.COURSE_GRADE +" ,"
			+ CourseStudentContract.COURSE_ENROLLMENT_STATUS +" ,"
			+ CourseStudentContract.SEMESTER +" ,"
			+ CourseStudentContract.YEAR
			+ " )"+
			" VALUES ('%s' ,'%s', '%s','%s', '%s', '%s')";


	static final String SELECT_COURSE_STUDENT_BY_ID = "SELECT * FROM "+ CourseStudentContract.TABLE_COURSE_STUDENT_NAME+" WHERE "+
			CourseStudentContract.ID+" = "+ "'%s' AND "+CourseStudentContract.STUDENT_UDID+" = '%s'" +
			" AND "+CourseStudentContract.SEMESTER+" = '%s'  AND "+CourseStudentContract.YEAR +" ='%s'";

	public static final String SELECT_COURSES_STUDENT_BY_ID = "SELECT * FROM "+ CourseStudentContract.TABLE_COURSE_STUDENT_NAME+" WHERE "+
			CourseStudentContract.STUDENT_UDID+" = '%s'";

	static final String SELECT_COURSES_COMPLETED_STUDENT_BY_ID_TERM = "SELECT * FROM "+ CourseStudentContract.TABLE_COURSE_STUDENT_NAME+" WHERE "+
			CourseStudentContract.STUDENT_UDID+" = '%s' " +
			" AND "+CourseStudentContract.COURSE_GRADE+" NOT LIKE 'I'"+
			" AND "+CourseStudentContract.SEMESTER+" ='%s' AND "+CourseStudentContract.YEAR +" = '%s' " +
			" AND "+CourseStudentContract.COURSE_ENROLLMENT_STATUS
			+" = '"+ Enrollment.ENROLLED.getValue()+"'";

	static final String SELECT_COURSES_COMPLETED_STUDENT_BY_ID = "SELECT * FROM "+ CourseStudentContract.TABLE_COURSE_STUDENT_NAME+" WHERE "+
			CourseStudentContract.STUDENT_UDID+" = '%s' " +
			" AND "+CourseStudentContract.COURSE_GRADE+" NOT LIKE 'I' AND "+CourseStudentContract.COURSE_ENROLLMENT_STATUS
			+" = '"+ Enrollment.ENROLLED.getValue()+"'";

	static final String UPDATE_COURSE_STUDENT_GRADE_BY_ID = "UPDATE "+ CourseStudentContract.TABLE_COURSE_STUDENT_NAME
			+" SET " +CourseStudentContract.COURSE_GRADE +" ='%s' WHERE  "
			+CourseStudentContract.ID+" = "+"'%s' AND "+
			CourseStudentContract.STUDENT_UDID+" = '%s' AND "+
			CourseStudentContract.SEMESTER+" = '%s'  AND "+CourseStudentContract.YEAR +" ='%s'";

	static final String UPDATE_COURSE_ENROLLMENT_STATUS_BY_ID = "UPDATE "+ CourseStudentContract.TABLE_COURSE_STUDENT_NAME
			+" SET " +CourseStudentContract.COURSE_ENROLLMENT_STATUS +" ='%s' WHERE  "
			+CourseStudentContract.ID+" = "+"'%s' AND "+
			CourseStudentContract.STUDENT_UDID+" = '%s' AND "+
			CourseStudentContract.SEMESTER+" = '%s'  AND "+CourseStudentContract.YEAR +" ='%s'";

	static final String SELECT_STUDENT_BY_ID_TERM = "SELECT * FROM "+ CourseStudentContract.TABLE_COURSE_STUDENT_NAME+" WHERE "+
			CourseStudentContract.ID+" = '%s' " +
			" AND "+CourseStudentContract.SEMESTER+" ='%s'  AND "+CourseStudentContract.YEAR +" ='%s'";
	static final String SELECT_COURSES_STUDENT_BY_ID_TERM = "SELECT * FROM "+ CourseStudentContract.TABLE_COURSE_STUDENT_NAME+" WHERE "+
			CourseStudentContract.STUDENT_UDID+" = '%s' AND "+CourseStudentContract.SEMESTER+" = '%s'  AND "+CourseStudentContract.YEAR +" ='%s'";

	static final String SELECT_STUDENT_BY_ENROLLMENT_TERM = "SELECT * FROM "+ CourseStudentContract.TABLE_COURSE_STUDENT_NAME+" WHERE "+
			CourseStudentContract.COURSE_ENROLLMENT_STATUS+" = '%s' " +
			" AND "+CourseStudentContract.SEMESTER+" ='%s'  AND "+CourseStudentContract.YEAR +" ='%s'";;

}
	
	
	
	
