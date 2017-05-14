package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import system.Student;

public class DbHelper {
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/?autoReconnect=true&useSSL=false";
	private static final Logger Log = Logger.getLogger( DbHelper.class.getName() );


	// Database credentials
	public static final String USER = "root";
	public static final String PASS = "1234";
	Connection conn = null;
	private static DbHelper dbHelper = null;

	private DbHelper() {
	}

	public static synchronized DbHelper getInstance() {
		if (dbHelper == null) {
			dbHelper = new DbHelper();
		}
		return dbHelper;
	}

	public void executeSQL(String sql) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException se) {
			Log.info("error execute sql "+se.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				Log.info("error "+se2.getMessage());
			}
		}
	}
	
	public ResultSet executeQuery(String sql) {
		ResultSet set = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			set = stmt.executeQuery(sql);
		} catch (SQLException se) {
			Log.info("error execute query sql "+se.getMessage());
		} 
		return set;
	}
	
	public void startJDBCConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			executeSQL(DbContract.CREATE_DATABASE);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void stopJDBCConnection() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
}
