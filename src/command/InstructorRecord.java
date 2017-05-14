package command;

import java.util.List;

import interfaces.Command;
import system.Course;

public class InstructorRecord implements Command {
	
	private Term term;
	private final String command;
	private final String instructorID;
	public InstructorRecord(String command,String instructorID,Command startTerm) {
		this.term = (Term) startTerm;
		this.command = command;
		this.instructorID = instructorID;
	}
	
	@Override
	public String execute() {
		System.out.println(command);
		System.out.println("> instructor,"+term.getInstructor(this.instructorID).getName());
		List<Course> courses = term.getInstructorCourses(this.instructorID);
		String result = "";
		for(Course course: courses) {
			result = result.concat("> "+course.getID()+","+course.getCourseTitle()+","+
					course.getCapacity(this.instructorID,term.getSemester(),term.getYear())
					+","+term.getYear()+","+term.getSemester()+"\n");
		}
		return result;
	}

}
