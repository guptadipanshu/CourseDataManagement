package database.course.prerequiste;

import database.DbContract;
import database.student.StudentContract;

public class CoursePreReqContract {

	public static final String TABLE_COURSE_PREREQ_NAME = DbContract.DB_NAME+".COURSE_PREREQ ";


	public static final String ID ="id";
	public static final String PRE_REQUISTE ="pre_req";

	static final String INSERT_COURSE_PREREQ = "INSERT INTO "
			+ CoursePreReqContract.TABLE_COURSE_PREREQ_NAME +" ("
			+ CoursePreReqContract.ID +" ,"
			+ CoursePreReqContract.PRE_REQUISTE
			+ " )"+
			" VALUES ('%s' ,'%s')";


	static final String SELECT_PRE_REQ_BY_ID = "SELECT * FROM "+ CoursePreReqContract.TABLE_COURSE_PREREQ_NAME+" WHERE "+
			CoursePreReqContract.ID+" = "+"'%s'";

	static final String DELETE_PRE_REQ_BY_ID = "DELETE  FROM "+ CoursePreReqContract.TABLE_COURSE_PREREQ_NAME+" WHERE "+
			CoursePreReqContract.ID+" = '%s' AND "+CoursePreReqContract.PRE_REQUISTE+" = '%s'";
}
	
	
	
	
