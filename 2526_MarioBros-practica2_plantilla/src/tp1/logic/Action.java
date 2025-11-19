package tp1.logic;



/**
 * Represents the allowed actions in the game
 *
 */
public enum Action {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), STOP(0,0);//las distintas acciones posibles del juego
	private int x;
	private int y;
	
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
}
