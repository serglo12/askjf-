package tp1.control.commands;

import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.exceptions.*;
import tp1.view.Messages;

public class AddObjectCommand extends AbstractCommand{
	 private static final String NAME = Messages.COMMAND_ADD_OBJECT_NAME;
	    private static final String SHORTCUT = Messages.COMMAND_ADD_OBJECT_SHORTCUT;
	    private static final String DETAILS = Messages.COMMAND_ADD_OBJECT_DETAILS;
	    private static final String HELP = Messages.COMMAND_ADD_OBJECT_HELP;
	    private String[] commandWords2;

	    public AddObjectCommand() {
			super(NAME, SHORTCUT, DETAILS, HELP);
		}
	    public AddObjectCommand(String[] commandWords) {
			super(NAME, SHORTCUT, DETAILS, HELP);
			commandWords2=commandWords;
		}
	    @Override
	    public Command parse(String[] commandWords) throws CommandParseException{//parse de addobject, devuelve addobject si coincide la primera palabra del array con el nombre o shortcut 
			Command c=null;
			if(matchCommandName(commandWords[0])&&commandWords.length>1) {
				c=new AddObjectCommand(commandWords);
			}
			else if(matchCommandName(commandWords[0]))throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);//si no hay nada despu�s de action da fallo
			return c;
	    }
	    
	    @Override
	    public void execute(GameModel game, GameView view) throws CommandExecuteException{//si es una sola palabra da fallo, en otro caso se llama a game, que a su vez llama a gameobjectfactory, donde se ve si es correcto o no
	    	try{
	    		if(game.addObject(commandWords2)) {//true si el objecto se ha leido correctamente
	    		view.showGame();
	    	}
	    	else {//si el objecto es incorrecto se lanza esta excepcion
	    		throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, new GameParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(String.join(" ", commandWords2))));
	    	}	
	    	}catch(OffBoardException e) {//la posicion esta fuera de tablero
				throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
			}
	    	catch(GameParseException e) {//aqui se engloba cualquier problema con el formato de los objetos, demasiados argumentos, argumento propio de cada objeto incorrecto,etc.
				throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
	    	}
	    }
}
