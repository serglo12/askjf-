package tp1.exceptions;

public class GameLoadException extends GameModelException{
	public GameLoadException() {
		super();
	}
	public GameLoadException(String s) {
		super(s);
	}
	public GameLoadException(String s, Exception e) {
		super(s,e);
	}
}
