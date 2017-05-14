package command;

import interfaces.Command;

public class StopTerm implements Command {
	
	private Term term;
	private final String command;

	public StopTerm(String command,Command startTerm) {
		this.term = (Term) startTerm;
		this.command = command;
	}

	@Override
	public String execute() {
		System.out.println(this.command);
		return "> end,"+term.getSemester()+","+term.getYear();
	}
}

