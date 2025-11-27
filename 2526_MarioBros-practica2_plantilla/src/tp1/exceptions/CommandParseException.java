package tp1.exceptions;

public class CommandParseException extends CommandException{
	public CommandParseException(String message) {
		super(message);
	}
	public CommandParseException(String message, Exception nfe) {
		super(message, nfe);
	}
}
