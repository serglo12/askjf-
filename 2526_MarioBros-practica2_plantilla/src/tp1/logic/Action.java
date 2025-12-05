package tp1.logic;

import tp1.exceptions.ActionParseException;
import tp1.view.Messages;

/**
 * Represents the allowed actions in the game
 *
 */
public enum Action {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), STOP(0,0);//las distintas acciones posibles del juego
	private int x;
	private int y;
	private static final String left=Messages.LEFT;
	private static final String left_shortcut=Messages.LEFT_SHORTCUT;
	private static final String right= Messages.RIGHT;
	private static final String right_shortcut=Messages.RIGHT_SHORTCUT;
	private static final String up_shortcut=Messages.UP_SHORTCUT;
	private static final String up=Messages.UP;
	private static final String down_shortcut=Messages.DOWN_SHORTCUT;
	private static final String down=Messages.DOWN;
	private static final String stop=Messages.STOP;
	private static final String stop_shortcut=Messages.STOP_SHORTCUT;


	private Action(int x, int y) {//Constructor de action
		this.x=x;
		this.y=y;
	}
	
	public int getX() {//devuelve la x de action
		return x;
	}

	public int getY() {//devuelve la y de action
		return y;
	}
	public boolean opuesto(Action action) {//devuelve true si la acci�n que se haya pasado es la opuesta de esta accion, si es stop no tiene opuesto, por lo tanto es falso.
		switch(this) {
		case LEFT: return action==Action.RIGHT;
		case RIGHT: return action==Action.LEFT;
		case UP: return action==Action.DOWN;
		case DOWN: return action==Action.UP;
		default: return false;
		}
	}
	
	public static void leerAcciones(String[] commandWords2, GameModel game) throws ActionParseException{/*Ahora mismo si hay varias acciones y una está mal, hace todas ignorado la que está mal.
	Si todas las que hay están mal salta el string de que todas están mal */
		int contador=1;
		for(int i=1; i<commandWords2.length;++i) {//el for va leyendo todas las acciones
			if(commandWords2[i].equalsIgnoreCase(up_shortcut)||commandWords2[i].equalsIgnoreCase(up)) {
				game.addAction(Action.UP);
			}
			else if(commandWords2[i].equalsIgnoreCase(down_shortcut)||commandWords2[i].equalsIgnoreCase(down)) {
				game.addAction(Action.DOWN);
			}
			else if(commandWords2[i].equalsIgnoreCase(left_shortcut)||commandWords2[i].equalsIgnoreCase(left)) {
				game.addAction(Action.LEFT);
			}
			else if(commandWords2[i].equalsIgnoreCase(right_shortcut)||commandWords2[i].equalsIgnoreCase(right)) {
				game.addAction(Action.RIGHT);
			}
			else if(commandWords2[i].equalsIgnoreCase(stop_shortcut)||commandWords2[i].equalsIgnoreCase(stop)) {
				game.addAction(Action.STOP);
			}
			else {
				contador+=1;
			}
		}
		if(contador==commandWords2.length)throw new ActionParseException(Messages.ACTION_INCORRECT_PARAMETER_NUMBER);//si todas las acciones son malas, se lanza esa excepcion
	}
	
	
	public static String devuelveString(Action a) {//dada la accion se devuelve el string que la representa
		String mov;
		switch (a) {
		case LEFT: mov=left; break;
		case RIGHT: mov=right; break;
		case STOP: mov=stop; break;
		case DOWN: mov=down; break;
		default: mov=up; break;
		}
		return mov;
	}
	
	public static Action devuelveMov(String mov) throws ActionParseException{//pasa el string de movimiento al action correspondiente 
		Action mov2=null;
		if(mov.equalsIgnoreCase(left)||mov.equalsIgnoreCase(left_shortcut))mov2=LEFT;
		else if(mov.equalsIgnoreCase(right)||mov.equalsIgnoreCase(right_shortcut))mov2=RIGHT;
		else if(mov.equalsIgnoreCase(stop)||mov.equalsIgnoreCase(stop_shortcut))mov2=STOP;
		else if(!(mov.equalsIgnoreCase(up)||mov.equalsIgnoreCase(up_shortcut)||mov.equalsIgnoreCase(down)||mov.equalsIgnoreCase(down_shortcut)))throw new ActionParseException(String.format(Messages.UNKNOWN_ACTION, mov));//si la accion no es correcta se lanza esta excepcion
		if(mov2==null)throw new ActionParseException();
		return mov2;
	}
}
