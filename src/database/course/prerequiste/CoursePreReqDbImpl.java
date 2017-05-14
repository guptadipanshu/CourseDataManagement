package database.course.prerequiste;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.DbContract;
import database.DbHelper;
import database.course.instructor.CourseInstructorContract;
import database.instructor.InstructorDb;
import database.instructor.InstructorDbImpl;
import database.student.StudentContract;
import system.Address;
import system.Course;
import system.Instructor;
import system.Student;

public class CoursePreReqDbImpl implements CoursePreReqDb {

	@Override
	public void createCoursePreReqTable() {
		DbHelper.getInstance().executeSQL(DbContract.CREATE_COURSE_PRE_REQ_TABLE);
	}

	@Override
	public void addPrerequiste(String courseID, String preReq) {

		String sql = String.format(CoursePreReqContract.INSERT_COURSE_PREREQ,courseID,preReq);
		DbHelper.getInstance().executeSQL(sql);

	}

	@Override
	public List<String> getPrerequiste(String courseID) {
		List<String> preReqList = new ArrayList<>();
		String sql = String.format(CoursePreReqContract.SELECT_PRE_REQ_BY_ID, courseID);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		if (set == null) {
			return preReqList;
		}

		try {
			while (set.next()) {
				String id = set.getString(CoursePreReqContract.PRE_REQUISTE);
				preReqList.add(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return preReqList;
	}

	@Override
	public void removePreReq(String courseID, String preRequisteCourseId) {
		String sql = String.format(CoursePreReqContract.DELETE_PRE_REQ_BY_ID,courseID,preRequisteCourseId);
		DbHelper.getInstance().executeSQL(sql);
	}

}
