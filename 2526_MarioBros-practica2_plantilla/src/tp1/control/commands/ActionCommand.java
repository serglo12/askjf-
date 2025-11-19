package tp1.control.commands;

import tp1.logic.*;
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
    
    public Command parse(String[] commandWords) {//parse de action, devuelve Action si coincide la primera palabra del array con el nombre o shortcut 
		Command c=null;
		if(matchCommandName(commandWords[0])) {
			c=this;
			this.commandWords2=commandWords;
		}
		return c;
	}
	
    
	public void execute(GameModel game, GameView view) { //ejecuta action
		if(commandWords2.length==1) {//si no hay nada despu�s de action da fallo
			view.showError(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
		else {//durante todo el array va añadiendo acciones, y si alguna no coincide se muestra unknown action
			for(int i=1; i<commandWords2.length;++i) {
				if(commandWords2[i].equals("u")||commandWords2[i].equals("up")) {
					game.addAction(Action.UP);
				}
				else if(commandWords2[i].equals("d")||commandWords2[i].equals("down")) {
					game.addAction(Action.DOWN);
				}
				else if(commandWords2[i].equals("l")||commandWords2[i].equals("left")) {
					game.addAction(Action.LEFT);
				}
				else if(commandWords2[i].equals("r")||commandWords2[i].equals("right")) {
					game.addAction(Action.RIGHT);
				}
				else if(commandWords2[i].equals("s")||commandWords2[i].equals("stop")) {
					game.addAction(Action.STOP);
				}
				else view.showError(String.format(Messages.UNKNOWN_ACTION, commandWords2[i]));
			}
			game.update();
			if(!game.marioHaPerdido())view.showGame();//solo muestra el juego si el juego no ha terminado
		}
	}
}
