package command;

import interfaces.Command;

public class NextTerm implements Command {
	
	private Term term;
	private final String command;
	public NextTerm(String command,Command startTerm) {
		this.term = (Term) startTerm;
		this.command = command;
	}

	@Override
	public String execute() {
		System.out.println(this.command);
		return "> begin,"+term.getNextTerm()+","+term.getYear();
	}
}
