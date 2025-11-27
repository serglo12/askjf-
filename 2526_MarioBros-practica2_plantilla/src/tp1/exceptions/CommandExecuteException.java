package tp1.exceptions;

public class CommandExecuteException extends CommandException{
	public CommandExecuteException(String message) {
		super(message);
	}
	public CommandExecuteException(String message, Exception nfe) {
		super(message, nfe);
	}
}
