package tp1.exceptions;


public class CommandException extends Exception{
	public CommandException(String message) {
		super(message);
	}
	public CommandException(String message, Exception nfe) {
		super(message, nfe);
	}
}
