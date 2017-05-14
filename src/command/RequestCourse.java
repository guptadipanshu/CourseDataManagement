package command;

import interfaces.Command;
import interfaces.Enrollment;

public class RequestCourse implements Command {
	
	private final String command;
	private final String studentID;
	private final String courseID;
	private Term term;
	
	public RequestCourse(String command, String studentID, String courseID, Command startTerm) {
		this.command = command;
		this.studentID = studentID;
		this.courseID = courseID;
		this.term = (Term) startTerm;
	}
	
	
	@Override
	public String execute() {
		System.out.println(command);
		String result;
		Enrollment type = term.enroll(studentID,courseID);
		if(type.equals(Enrollment.ENROLLED)) {
			term.recordGrade(studentID,courseID,"I");
			result ="> enrolled";
		}else if(type.equals(Enrollment.MISSING_PREREQ)) {
			result ="> not enrolled - missing prerequisites";
		}else {
			result = "> not enrolled - no available seats";
		}
		return result;
	}

}
