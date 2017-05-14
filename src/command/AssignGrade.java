package command;

import interfaces.Command;

public class AssignGrade implements Command {
	
	private final String command;
	private final String studentID;
	private final String courseID;
	private final String grade;
	private Term term;
	
	public AssignGrade(String command, String studentID, String courseID, String grade,Command startTerm) {
		this.command = command;
		this.studentID = studentID;
		this.courseID = courseID;
		this.term = (Term) startTerm;
		this.grade = grade;
	}
	
	@Override
	public String execute() {
		System.out.println(command);
		return term.recordGrade(studentID,courseID,grade);

	}

}
