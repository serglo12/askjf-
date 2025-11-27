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
	
	public static void leerAcciones(String[] commandWords2, GameModel game) throws ActionParseException{
		int contador=1;
		for(int i=1; i<commandWords2.length;++i) {
			if(commandWords2[i].equals(up_shortcut)||commandWords2[i].equals(up)) {
				game.addAction(Action.UP);
			}
			else if(commandWords2[i].equals(down_shortcut)||commandWords2[i].equals(down)) {
				game.addAction(Action.DOWN);
			}
			else if(commandWords2[i].equals(left_shortcut)||commandWords2[i].equals(left)) {
				game.addAction(Action.LEFT);
			}
			else if(commandWords2[i].equals(right_shortcut)||commandWords2[i].equals(right)) {
				game.addAction(Action.RIGHT);
			}
			else if(commandWords2[i].equals(stop_shortcut)||commandWords2[i].equals(stop)) {
				game.addAction(Action.STOP);
			}
			else {
				contador+=1;
			}
		}
		if(contador==commandWords2.length)throw new ActionParseException(Messages.ACTION_INCORRECT_PARAMETER_NUMBER);

		
	}
	
	
	
	public static Action devuelveMov(String mov) throws ActionParseException{//pasa el string de movimiento al action correspondiente 
		Action mov2=null;
		switch(mov) {
		case left:mov2=Action.LEFT; break;
		case left_shortcut: mov2=Action.LEFT; break;
		case right: mov2=Action.RIGHT; break;
		case right_shortcut: mov2=Action.RIGHT; break;
		case up: break;
		case up_shortcut: break;
		case down: break;
		case down_shortcut: break;
		case stop: break;
		case stop_shortcut: break;
		default: throw new ActionParseException(String.format(Messages.UNKNOWN_ACTION, mov));
		}
		if(mov2==null)throw new ActionParseException();
		return mov2;
	}
}
