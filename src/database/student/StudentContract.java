package database.student;

import database.DbContract;

public class StudentContract {
	public static final String TABLE_NAME = DbContract.DB_NAME+".STUDENT ";
	public static final String UDID ="udid";
	public static final String NAME ="name";
	public static final String NUMBER ="phone_number";
	public static final String ADDRESS ="address";
	
	static final String QUERY_STUDENT_BY_ID = "SELECT * FROM "+ StudentContract.TABLE_NAME
			+ " WHERE " +StudentContract.UDID +" = '%s'";
	
	public static final String QUERY_ALL_STUDENT = "SELECT * FROM "+ StudentContract.TABLE_NAME;
	
	static final String INSERT_STUDENT = "INSERT INTO "
			+ StudentContract.TABLE_NAME +" ("
			+ StudentContract.UDID +" ,"
			+ StudentContract.NAME +" ,"
			+ StudentContract.NUMBER +" ,"
			+ StudentContract.ADDRESS +" "
					+ ")"+
			"VALUES ('%s' ,'%s', '%s','%s')";

	static final String UPDATE_STUDENT = "UPDATE "+ TABLE_NAME
			+" SET " +StudentContract.NAME +" ='%s' , " + StudentContract.NUMBER +" ='%s' , "+StudentContract.ADDRESS+ " = '%s'"+
			" WHERE  " +StudentContract.UDID+" = "+"'%s'";


}
