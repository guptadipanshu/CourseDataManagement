package database.course.student;

import database.DbContract;
import database.DbHelper;
import database.course.CourseDb;
import database.course.CourseDbImpl;
import system.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseStudentDbImpl implements CourseStudentDb {

	@Override
	public void createCourseStudentTable() {
		DbHelper.getInstance().executeSQL(DbContract.CREATE_COURSE_STUDENT_TABLE);
	}

	@Override
	public void addCourseGrade(String studentID, String courseID, String grade, String semester,String year,String enrollmentStatus) {
		String sql = String.format(CourseStudentContract.SELECT_COURSE_STUDENT_BY_ID, courseID,studentID, semester,year);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		try{
			if(set == null || !set.next()) {
				sql  = String.format(CourseStudentContract.INSERT_COURSE_STUDENT, courseID,studentID,grade,enrollmentStatus, semester, year);
				DbHelper.getInstance().executeSQL(sql);
			}else {
				sql  = String.format(CourseStudentContract.UPDATE_COURSE_STUDENT_GRADE_BY_ID, grade,courseID,studentID, semester, year);
				DbHelper.getInstance().executeSQL(sql);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Record> getRecordAllTerms(String studentId) {
		String sql = String.format(CourseStudentContract.SELECT_COURSES_STUDENT_BY_ID, studentId);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		return getRecords(set);
	}

	@Override
	public List<Record> getCompletedCourseByTerm(String studentId,List<Course.Term> terms) {
		List<Record> records = new ArrayList<>();
		for(Course.Term term: terms) {
			String sql = String.format(CourseStudentContract.SELECT_COURSES_COMPLETED_STUDENT_BY_ID_TERM, studentId, term.semester,term.year);
			ResultSet set = DbHelper.getInstance().executeQuery(sql);
			records.addAll(getRecords(set));
		}
		return records;
	}

	@Override
	public List<Record> getCompletedCourse(String studentId) {
		List<Record> records = new ArrayList<>();
		String sql = String.format(CourseStudentContract.SELECT_COURSES_COMPLETED_STUDENT_BY_ID, studentId);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		records.addAll(getRecords(set));
		return records;
	}

	@Override
	public void setEnrollmentStatus(String courseID, String studentId, String enrollmentStatus, String semester,String year) {
		String sql = String.format(CourseStudentContract.SELECT_COURSE_STUDENT_BY_ID, courseID,studentId,semester,year);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		try{
			if(set == null || !set.next()) {
				sql  = String.format(CourseStudentContract.INSERT_COURSE_STUDENT, courseID,studentId,null,enrollmentStatus,semester,year);
				DbHelper.getInstance().executeSQL(sql);
			}else {
				sql  = String.format(CourseStudentContract.UPDATE_COURSE_ENROLLMENT_STATUS_BY_ID, enrollmentStatus,
						courseID,studentId,semester, year);
				DbHelper.getInstance().executeSQL(sql);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<String> getEnrolledStudentsByTerm(String courseId, String semester,String year) {
		List<String> studentIdList = new ArrayList<>();
		String sql = String.format(CourseStudentContract.SELECT_STUDENT_BY_ID_TERM, courseId, semester,year);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		if (set == null) {
			return studentIdList;
		}

		try {
			while (set.next()) {
				String id = set.getString(CourseStudentContract.STUDENT_UDID);
				studentIdList.add(id);
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
		return studentIdList;
	}

	@Override
	public List<Record> getGradesByTerm(String id, String semester,String year) {
		String sql = String.format(CourseStudentContract.SELECT_COURSES_STUDENT_BY_ID_TERM, id,semester,year);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		return getRecords(set);
	}

	@Override
	public List<Course> getWaitListedCourse(String semester, String year, String enrollmentStatus) {
		List<Course> studentWaitList = new ArrayList<>();
		String sql = String.format(CourseStudentContract.SELECT_STUDENT_BY_ENROLLMENT_TERM, enrollmentStatus, semester,year);
		ResultSet set = DbHelper.getInstance().executeQuery(sql);
		if (set == null) {
			return studentWaitList;
		}

		try {
			while (set.next()) {
				String id = set.getString(CourseStudentContract.ID);
				CourseDb courseDb = new CourseDbImpl();
				studentWaitList.add(courseDb.getCourse(id));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return studentWaitList;
	}

	private List<Record> getRecords(ResultSet set) {
		List<Record> records = new ArrayList<>();
		if (set == null) {
			return records;
		}

		try {
			while (set.next()) {
				String id = set.getString(CourseStudentContract.ID);
				String semester = set.getString(CourseStudentContract.SEMESTER);
				String year = set.getString(CourseStudentContract.YEAR);
				String grade = set.getString(CourseStudentContract.COURSE_GRADE);
				String enrollment = set.getString(CourseStudentContract.COURSE_ENROLLMENT_STATUS);
				Record record = new Record(id, semester, grade,enrollment, year);
				records.add(record);
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
		return records;
	}

	public static class Record {
		public final String courseID;
		public final String semester;
		public final String grade;
		public final String enrollmentStatus;
		public final String year;

		Record(String courseID, String term, String grade, String enrollmentStatus, String year) {
			this.courseID = courseID;
			this.semester = term;
			this.grade = grade;
			this.enrollmentStatus = enrollmentStatus;
			this.year = year;
		}
	}
}
