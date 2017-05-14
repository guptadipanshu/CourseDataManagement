package database;

import database.course.CourseContract;
import database.course.instructor.CourseInstructorContract;
import database.course.prerequiste.CoursePreReqContract;
import database.course.student.CourseStudentContract;
import database.instructor.InstructoraContract;
import database.student.StudentContract;

public class DbContract {
	public static final String DB_NAME = "COURSE_MANAGEMENT";
	static final String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS "+DB_NAME;
	
	public static final String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS "+
			StudentContract.TABLE_NAME +" ( "+ 
			StudentContract.UDID +" VARCHAR(100) NOT NULL, " + 
			StudentContract.NAME +" VARCHAR (100) NOT NULL, " + 
			StudentContract.NUMBER +" VARCHAR (10) NOT NULL, " +
			StudentContract.ADDRESS +" VARCHAR(100) NOT NULL, " +
			"PRIMARY KEY ("
			+ StudentContract.UDID+")" + ")";

	public static final String CREATE_INSTRUCTOR_TABLE = "CREATE TABLE IF NOT EXISTS "+
			InstructoraContract.TABLE_NAME +" ( "+
			InstructoraContract.UDID +" VARCHAR(100) NOT NULL, " +
			InstructoraContract.NAME +" VARCHAR (100) NOT NULL, " +
			InstructoraContract.NUMBER +" VARCHAR (10) NOT NULL, " +
			InstructoraContract.ADDRESS +" VARCHAR(100) NOT NULL, " +
			"PRIMARY KEY ("
			+ InstructoraContract.UDID+")" + ")";
		
	public static final String CREATE_COURSE_TABLE = "CREATE TABLE IF NOT EXISTS "+
			CourseContract.TABLE_COURSE_NAME +" ( "+ 
			CourseContract.ID +" VARCHAR(100) NOT NULL, " + 
			CourseContract.TITLE +" VARCHAR (100) NOT NULL, " + 
			CourseContract.SEAT_AVAILABLE +" INTEGER, " +
			CourseContract.SEMESTER +" VARCHAR (20) NOT NULL, " +
			CourseContract.YEAR +" VARCHAR (10) NOT NULL, " +
			"PRIMARY KEY ("+
				CourseContract.ID+", "+
				CourseContract.SEMESTER+ " , " +
				CourseContract.YEAR +
					")" 
			+ ")";
	
	public static final String CREATE_COURSE_PRE_REQ_TABLE = "CREATE TABLE IF NOT EXISTS "+
			CoursePreReqContract.TABLE_COURSE_PREREQ_NAME +" ( "+
			CoursePreReqContract.ID +" VARCHAR(100) NOT NULL, " +
			CoursePreReqContract.PRE_REQUISTE +" VARCHAR(100) NOT NULL, " +
			"PRIMARY KEY ("+
			CoursePreReqContract.ID+", "+
			CoursePreReqContract.PRE_REQUISTE +
					")"
			+ ")";

	public static final String CREATE_COURSE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS "+
			CourseStudentContract.TABLE_COURSE_STUDENT_NAME +" ( "+
			CourseStudentContract.ID +" VARCHAR(100) NOT NULL, " +
			CourseStudentContract.STUDENT_UDID +" VARCHAR (20) NOT NULL, " +
			CourseStudentContract.COURSE_GRADE +" VARCHAR (20) NOT NULL, " +
			CourseStudentContract.COURSE_ENROLLMENT_STATUS +" VARCHAR (20), " +
			CourseStudentContract.SEMESTER +" VARCHAR (20) NOT NULL, " +
			CourseStudentContract.YEAR +" VARCHAR (10), " +
			"PRIMARY KEY ("+
			CourseStudentContract.ID+", "+
			CourseStudentContract.STUDENT_UDID +" , "+
			CourseStudentContract.SEMESTER + " , "+
			CourseStudentContract.YEAR +
					")"
			+ ")";

	public static final String CREATE_COURSE_INSTRUCTOR_TABLE = "CREATE TABLE IF NOT EXISTS "+
			CourseInstructorContract.TABLE_COURSE_INSTRUCOTR_NAME +" ( "+
			CourseInstructorContract.ID +" VARCHAR(100) NOT NULL, " +
			CourseInstructorContract.INSTRUCTOR_UDID +" VARCHAR (10) NOT NULL, " +
			CourseInstructorContract.CAPACITY +" INTEGER, " +
			CourseInstructorContract.SEMESTER +" VARCHAR (20) NOT NULL, " +
			CourseInstructorContract.YEAR +" VARCHAR (10) NOT NULL, " +

			"PRIMARY KEY ("+
			CourseInstructorContract.ID+", "+
			CourseInstructorContract.INSTRUCTOR_UDID +" , "+
			CourseInstructorContract.SEMESTER + " , "+
			CourseInstructorContract.YEAR +
			")"
			+ ")";
}
