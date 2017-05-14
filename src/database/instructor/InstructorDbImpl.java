package database.instructor;

import database.DbContract;
import database.DbHelper;
import system.Address;
import system.Instructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dipanshugupta on 4/15/17.
 */
public class InstructorDbImpl implements InstructorDb {
    @Override
    public void createInstructorDb() {
        DbHelper.getInstance().executeSQL(DbContract.CREATE_INSTRUCTOR_TABLE);
    }

    @Override
    public void insertInstructor(Instructor instructor) {
        final Address addr = instructor.getAddress();
        final String address = addr.getHouseNumber() +" "+addr.getStreetName()+" "+addr.getZipCode();
        final String sql = String.format(InstructoraContract.INSERT_INSTRUCTOR, instructor.getID(), instructor.getName(), instructor.getNumber(),
                address);
        DbHelper.getInstance().executeSQL(sql);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        List<Instructor> instructorList = null;
        ResultSet set = DbHelper.getInstance().executeQuery(InstructoraContract.QUERY_ALL_INSTRUCTOR);
        if (set == null) {
            return instructorList;
        }

        try {
            instructorList = new ArrayList<>();
            while (set.next()) {
                String id = set.getString(InstructoraContract.UDID);
                String name = set.getString(InstructoraContract.NAME);
                String number = set.getString(InstructoraContract.NUMBER);
                String address = set.getString(InstructoraContract.ADDRESS);
                Instructor instructor = new Instructor(id,name,address,number);
                instructorList.add(instructor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return instructorList;
    }

    @Override
    public Instructor getInstructor(String instructorId) {
        Instructor instructor = null;
        String sql = String.format(InstructoraContract.QUERY_INSTRUCTOR_BY_ID, instructorId);
        ResultSet set = DbHelper.getInstance().executeQuery(sql);
        if (set == null) {
            return instructor;
        }

        try {
            if (set.next()) {
                String id = set.getString(InstructoraContract.UDID);
                String name = set.getString(InstructoraContract.NAME);
                String number = set.getString(InstructoraContract.NUMBER);
                String address = set.getString(InstructoraContract.ADDRESS);
                instructor = new Instructor(id,name,address,number);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instructor;
    }

    @Override
    public boolean updateInstructor(String instructorId, String name, String address, String number) {
        String sql = String.format(InstructoraContract.UPDATE_INSTRUCTOR,name,number,address,instructorId);
        DbHelper.getInstance().executeSQL(sql);
        return true;
    }
}
