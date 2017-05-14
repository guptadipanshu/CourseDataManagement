package database.course.instructor;

import database.DbContract;
import database.DbHelper;
import database.course.CourseDb;
import database.course.CourseDbImpl;
import database.instructor.InstructorDb;
import database.instructor.InstructorDbImpl;
import system.Course;
import system.Instructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseInstructorDbImpl implements CourseInstructorDb {

	@Override
	public void createCourseInstructorTable() {
		DbHelper.getInstance().executeSQL(DbContract.CREATE_COURSE_INSTRUCTOR_TABLE);
	}


	@Override
	public void insertInstructor(String instructorID, String courseID, String semester,String year, int initialCapacity) {
		String sql = String.format(CourseInstructorContract.INSERT_COURSE_INSTRUCTOR,courseID,instructorID,initialCapacity,semester,year);
		DbHelper.getInstance().executeSQL(sql);
		CourseDb courseDb = new CourseDbImpl();
		courseDb.insertCourseByInstructor(courseID,semester,year);
	}

	@Override
	public List<Instructor> getAllInstructorTeachingCourses(String semester, String year) {
		List<Instructor> instructorList = new ArrayList<>();
		String sql = String.format(CourseInstructorContract.SELECT_COURSES_INSTRUCTOR_BY_TERM, semester,year);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		if (set == null) {
			return instructorList;
		}

		try {
			while (set.next()) {
				String id = set.getString(CourseInstructorContract.INSTRUCTOR_UDID);
				InstructorDb instructorDb = new InstructorDbImpl();
				Instructor instructor = instructorDb.getInstructor(id);
				instructorList.add(instructor);
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
		return instructorList;
	}

	@Override
	public List<String> getCourseIdInstructorTeachingPerTerm(String instructorID,String semester,String year) {
		List<String> courseIDList = new ArrayList<>();
		String sql = String.format(CourseInstructorContract.SELECT_INSTRUCTOR_COURSES_BY_TERM,instructorID,semester,year);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		if (set == null) {
			return courseIDList;
		}

		try {
			while (set.next()) {
				String id = set.getString(CourseInstructorContract.ID);
				courseIDList.add(id);
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
		return courseIDList;
	}

	@Override
	public int getTotalCapcity(String courseID, String semester,String year) {
		int total =0;
		String sql = String.format(CourseInstructorContract.SELECT_COURSES_CAPACITY_BY_ID, courseID,semester,year);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		if (set == null) {
			return total;
		}

		try {
			while (set.next()) {
				total += set.getInt(CourseInstructorContract.CAPACITY);
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
		return total;
	}

	@Override
	public int getCapacity(String courseID, String instructorID, String semester,String year) {
		int total =0;
		String sql = String.format(CourseInstructorContract.SELECT_COURSES_CAPACITY_BY_ID_INSTRUCTOR, courseID,semester,year,instructorID);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		if (set == null) {
			return total;
		}

		try {
			if (set.next()) {
				total = set.getInt(CourseInstructorContract.CAPACITY);
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
		return total;
	}

	@Override
	public void updateCapacity(String courseID, String instructorID, String semester, String year, int newCapacity) {
		String sql = String.format(CourseInstructorContract.UPDATE_INSTRUCTOR_CAPACITY_BY_ID, newCapacity,courseID,instructorID,semester,year);
		DbHelper.getInstance().executeSQL(sql);
	}
}
