package tp1.control.commands;

import tp1.logic.*;
import tp1.exceptions.*;
import tp1.view.GameView;
import tp1.view.Messages;

public class ActionCommand extends AbstractCommand{
	private static final String NAME = Messages.COMMAND_ACTION_NAME;
    private static final String SHORTCUT =  Messages.COMMAND_ACTION_SHORTCUT;
    private static final String DETAILS =  Messages.COMMAND_ACTION_DETAILS;
    private static final String HELP =  Messages.COMMAND_ACTION_HELP;
    
    private String[] commandWords2;
    
    public ActionCommand() {//constructor de action
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
    public ActionCommand(String[] commandWords) {//constructor de action
		super(NAME, SHORTCUT, DETAILS, HELP);
		commandWords2=commandWords;
	}
    
    public Command parse(String[] commandWords) throws CommandParseException{//parse de action, devuelve Action si coincide la primera palabra del array con el nombre o shortcut 
		Command c=null;
		if(matchCommandName(commandWords[0])&&commandWords.length>1) {
			c=new ActionCommand(commandWords);
		}
		else if(matchCommandName(commandWords[0]))throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		return c;
	}
	
    
	public void execute(GameModel game, GameView view) throws CommandExecuteException{ //ejecuta action
		//durante todo el array va añadiendo acciones, y si alguna no coincide se muestra unknown action
			try {
				Action.leerAcciones(commandWords2, game);
			}catch(ActionParseException a) {
				throw new CommandExecuteException(a.getMessage());
			}
			game.update();
			if(!game.marioHaPerdido())view.showGame();//solo muestra el juego si el juego no ha terminado
		}
	}