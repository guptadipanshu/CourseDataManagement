package database.student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DbContract;
import database.DbHelper;
import system.Address;
import system.Student;

public class StudentDbImpl implements StudentDb {

	@Override
	public void createStudentTable() {
		DbHelper.getInstance().executeSQL(DbContract.CREATE_STUDENT_TABLE);
	}

	@Override
	public void insertStudent(Student student) {
		final Address addr = student.getAddress();
		final String address = addr.getHouseNumber() +" "+addr.getStreetName()+" "+addr.getZipCode();
		final String sql = String.format(StudentContract.INSERT_STUDENT, student.getID(), student.getName(), student.getNumber(),
				address);
		DbHelper.getInstance().executeSQL(sql);
	}

	@Override
	public Student getStudent(String udid) {
		Student student = null;
		String sql = String.format(StudentContract.QUERY_STUDENT_BY_ID, udid);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		if (set == null) {
			return student;
		}
		
		try {
			if (set.next()) {
				String id = set.getString(StudentContract.UDID);
				String name = set.getString(StudentContract.NAME);
				String number = set.getString(StudentContract.NUMBER);
				String address = set.getString(StudentContract.ADDRESS);
				student = new Student(id,name,address,number);
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
		return student;
	}

	@Override
	public List<Student> getAllStudent() {
		List<Student> studentList = null;
		ResultSet set = DbHelper.getInstance().executeQuery(StudentContract.QUERY_ALL_STUDENT);
		if (set == null) {
			return studentList;
		}
		
		try {
			studentList = new ArrayList<>();
			while (set.next()) {
				String id = set.getString(StudentContract.UDID);
				String name = set.getString(StudentContract.NAME);
				String number = set.getString(StudentContract.NUMBER);
				String address = set.getString(StudentContract.ADDRESS);
				Student student = new Student(id,name,address,number);
				studentList.add(student);
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
		
		return studentList;
	}

	@Override
	public boolean updateStudent(String studentId, String name, String address, String number) {
		String sql = String.format(StudentContract.UPDATE_STUDENT,name,number,address,studentId);
		DbHelper.getInstance().executeSQL(sql);
		return true;
	}

}
