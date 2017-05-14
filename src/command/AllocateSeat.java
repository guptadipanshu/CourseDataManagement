package command;

import java.util.Map;

import interfaces.Command;
import system.Course;

public class AllocateSeat implements Command {
	
	private final String command;
	private final String instructorID;
	private final String courseID;
	private String capacity;
	private Term term;
	
	public AllocateSeat(String command, String instructorID, String courseID, String capacity, Command startTerm) {
		this.command = command;
		this.instructorID = instructorID;
		this.courseID = courseID;
		this.capacity = capacity;
		this.term = (Term) startTerm;
		
	}
	
	@Override
	public String execute() {
		System.out.println(this.command);
		term.addInstructor(instructorID, courseID, Integer.parseInt(capacity));
		int available = term.getSeatAvailable(courseID);
		if(available <=0) {
			return "> warning - total,"+term.getCapacity(courseID) + ",available,0";
		}else {
			return "> total,"+term.getCapacity(courseID) + ",available,"+available;

		}
	}

}
