package database.instructor;

import database.DbContract;
import database.student.StudentContract;

/**
 * Created by dipanshugupta on 4/15/17.
 */
public class InstructoraContract {
    public static final String TABLE_NAME = DbContract.DB_NAME+".INSTRUCTOR ";
    public static final String UDID ="udid";
    public static final String NAME ="name";
    public static final String NUMBER ="phone_number";
    public static final String ADDRESS ="address";

    static final String QUERY_INSTRUCTOR_BY_ID = "SELECT * FROM "+ InstructoraContract.TABLE_NAME
            + " WHERE " +StudentContract.UDID +" = '%s'";

    static final String QUERY_ALL_INSTRUCTOR = "SELECT * FROM "+ InstructoraContract.TABLE_NAME;

    static final String INSERT_INSTRUCTOR = "INSERT INTO "
            + InstructoraContract.TABLE_NAME +" ("
            + InstructoraContract.UDID +" ,"
            + InstructoraContract.NAME +" ,"
            + InstructoraContract.NUMBER +" ,"
            + InstructoraContract.ADDRESS +" "
            + ")"+
            "VALUES ('%s' ,'%s', '%s','%s')";

    static final String UPDATE_INSTRUCTOR = "UPDATE "+ TABLE_NAME
            +" SET " +InstructoraContract.NAME +" ='%s' , " + InstructoraContract.NUMBER +" ='%s' , "+InstructoraContract.ADDRESS+ " = '%s'"+
            " WHERE  " +InstructoraContract.UDID+" = "+"'%s'";

}
