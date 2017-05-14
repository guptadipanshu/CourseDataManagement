package system;

import interfaces.Command;
import interfaces.SystemManager;

import java.io.File;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		final SystemManager systemManager = new SystemManagerImpl();
		final File studentFile = new File("students.csv");
		final File courseFile = new File("courses.csv");
		final File instuctorFile = new File("instructors.csv");
		final File termFile = new File("terms.csv");
		final File preReqFile = new File("prereqs.csv");
		final File commandFile = new File("actions.csv");
		systemManager.loadStudents(studentFile);
		systemManager.loadCourses(courseFile,termFile);
		systemManager.loadInstructors(instuctorFile);
		systemManager.loadPreRequisites(preReqFile);
		systemManager.loadCommand(commandFile);
		List<Command> commands = systemManager.getCommands();
		for(Command command: commands) {
			System.out.println(command.execute());
		}

		String dataAnalysisResults = systemManager.executeDataAnalysis();
		System.out.println(dataAnalysisResults);

		System.out.println(systemManager.addPreRequiste("5","17"));
//		Student student = systemManager.getStudent("4");
//		System.out.println("result is "+student.getID()+" " +student.getName() +" "+student.getNumber() +" "+student.getAddress().getHouseNumber());
//		
//		List<Student> studentList = systemManager.getStudents();
//		for(Student student2: studentList) {
//			System.out.println(student2.getID()+" " +student2.getName() +" "+student2.getNumber() +" "+student2.getAddress().getHouseNumber());
//		}

	}
}
