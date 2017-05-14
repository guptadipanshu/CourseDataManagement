package database.student;

import java.util.List;

import system.Student;

public interface StudentDb {
	void createStudentTable();
	void insertStudent(Student student);
	Student getStudent(String udid);
	List<Student> getAllStudent();

	boolean updateStudent(String studentId, String name, String address, String number);
}
