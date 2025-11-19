package tp1.control.commands;

import tp1.logic.GameModel;
import tp1.view.GameView;
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
	    @Override
	    public Command parse(String[] commandWords) {//parse de addobject, devuelve addobject si coincide la primera palabra del array con el nombre o shortcut 
			Command c=null;
			if(matchCommandName(commandWords[0])) {
				c=this;
				this.commandWords2=commandWords;
			}
			return c;
	    }
	    @Override
	    public void execute(GameModel game, GameView view) {//si es una sola palabra da fallo, en otro caso se llama a game, que a su vez llama a gameobjectfactory, donde se ve si es correcto o no
	    	if(commandWords2.length==1) {//si no hay nada despu�s de action da fallo
				view.showError(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			}
	    	else {
	    		if(game.addObject(commandWords2)) {//true si el objecto se ha leido correctamente
	    			view.showGame();
	    		}
	    		else view.showError(Messages.INVALID_GAME_OBJECT.formatted(String.join(" ", commandWords2))); //si el objecto es incorrecto
	    	}
	    }
	    
}
