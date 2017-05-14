package database.instructor;

import system.Instructor;

import java.util.List;

/**
 * Created by dipanshugupta on 4/15/17.
 */
public interface InstructorDb {
    void createInstructorDb();

    void insertInstructor(Instructor instructor);

    List<Instructor> getAllInstructors();

    Instructor getInstructor(String instructorId);

    boolean updateInstructor(String instructorId, String name, String address, String number);
}
