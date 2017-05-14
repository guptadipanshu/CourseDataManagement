package database.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DbContract;
import database.DbHelper;
import database.course.instructor.CourseInstructorContract;
import system.Course;

public class CourseDbImpl implements CourseDb {

	@Override
	public void createCourseTable() {
		DbHelper.getInstance().executeSQL(DbContract.CREATE_COURSE_TABLE);
	}

	@Override
	public void insertCourse(Course course) {
		List<Course.Term> semester = course.getSemester();
		for (Course.Term term : semester) {
			String sql = String.format(CourseContract.INSERT_COURSE, course.getID(), course.getCourseTitle(), "0",
					term.semester,term.year);
			DbHelper.getInstance().executeSQL(sql);
		}
	}

	@Override
	public void insertCourseByInstructor(String courseId, String semester, String year) {
		Course course = getCourse(courseId);
		if(course != null) {
			int totalSeats = getSeatsAvailable(courseId,semester,year);
			String sql = String.format(CourseContract.INSERT_COURSE, courseId, course.getCourseTitle(),String.valueOf(totalSeats),
					semester, year);
			DbHelper.getInstance().executeSQL(sql);
		}

	}

	@Override
	public Course getCourse(String udid) {
		Course course = null;
		String sql = String.format(CourseContract.SELECT_COURSE_BY_ID, udid);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		if (set == null) {
			return course;
		}

		try {
			if (set.next()) {
				String id = set.getString(CourseContract.ID);
				String title = set.getString(CourseContract.TITLE);
				List<Course.Term> termList = new ArrayList();
				String semseter = set.getString(CourseContract.SEMESTER);
				String year = set.getString(CourseContract.YEAR);
				termList.add(new Course.Term(semseter,year));
				while (set.next()) {
					semseter = set.getString(CourseContract.SEMESTER);
					year = set.getString(CourseContract.YEAR);
					termList.add(new Course.Term(semseter,year));
				}
				course = new Course(id, title, termList);
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
		return course;
	}

	@Override
	public List<Course> getAllCourses() {
		List<Course> courseList = new ArrayList<>();
		ResultSet set = DbHelper.getInstance().executeQuery(CourseContract.SELECT_ALL_COURSE);
		if (set == null) {
			return courseList;
		}

		try {
			while (set.next()) {
				String id = set.getString(CourseContract.ID);
				courseList.add(getCourse(id));
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
		return courseList;
	}

	@Override
	public int getSeatsAvailable(String courseID, String semester,String year) {
		int total =0;
		String sql = String.format(CourseContract.GET_COURSE_SEATS_BY_TERM, courseID,semester,year);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		if (set == null) {
			return total;
		}

		try {
			if (set.next()) {
				total = set.getInt(CourseContract.SEAT_AVAILABLE);
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
	public void updateSeatAvailable(String courseID, String semester,String year, int seatCapacity) {
		String sql = String.format(CourseContract.UPDATE_SEATS_AVAILABLE,seatCapacity,courseID,semester,year);
		DbHelper.getInstance().executeSQL(sql);
	}

	@Override
	public List<Course> getAllCoursesByTerm(String semester,String year) {
		List<Course> courseList = new ArrayList<>();
		String sql = String.format(CourseContract.SELECT_ALL_COURSE_BY_TERM,semester,year);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		if (set == null) {
			return courseList;
		}

		try {
			while (set.next()) {
				String id = set.getString(CourseContract.ID);
				courseList.add(getCourse(id));
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
		return courseList;
	}

	@Override
	public boolean updateCourse(String courseId, String title) {
		String sql = String.format(CourseContract.UPDATE_COURSE,title,courseId);
		DbHelper.getInstance().executeSQL(sql);
		return true;
	}

}
