package command;

import database.course.student.CourseStudentDbImpl;
import interfaces.Command;

import java.util.List;

public class StudentRecord implements Command {
	
	private Term term;
	private final String command;
	private final String studentID;
	
	public StudentRecord(String command, String studentID, Command startTerm) {
		this.command = command;
		this.studentID = studentID;
		this.term = (Term) startTerm;	
	}

	@Override
	public String execute() {
		System.out.println(command);
		System.out.println("> student,"+term.getStudentName(this.studentID));
		List<CourseStudentDbImpl.Record> records = term.getStudentRecord(this.studentID);
		String result ="";
		for(CourseStudentDbImpl.Record record: records) {
			result = result.concat("> "+record.courseID+","+term.getCourseTitle(record.courseID)+","+
					record.grade+","+record.semester+","+record.year+"\n");
		}
		return result;
	}

}
