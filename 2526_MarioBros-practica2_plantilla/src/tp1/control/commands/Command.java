package tp1.control.commands;

import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.exceptions.*;

public interface Command {

	public void execute(GameModel game, GameView view) throws CommandExecuteException;	  
	public Command parse(String[] commandWords) throws CommandParseException;
	public String helpText();
}
