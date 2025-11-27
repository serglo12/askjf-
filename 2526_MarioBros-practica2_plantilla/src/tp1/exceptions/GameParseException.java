package tp1.exceptions;

public class GameParseException extends GameModelException{
	public GameParseException(String message) {
			super(message);
		}
	public GameParseException(String message, Exception nfe) {
		super(message, nfe);
	}
	public GameParseException() {
		super();
	}
}
