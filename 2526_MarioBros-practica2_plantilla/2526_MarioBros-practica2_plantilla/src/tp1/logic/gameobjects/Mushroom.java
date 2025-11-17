
package tp1.logic.gameobjects;
import tp1.view.Messages;
import tp1.logic.*;


public class Mushroom extends MovingObject{	
	private static final String NAME=Messages.MUSHROOM_NAME;
	private static final String SHORTCUT=Messages.MUSHROOM_SHORTCUT;	
	
	public Mushroom(GameWorld game, Position pos) {//constructor del mushroom
		super(game, pos);
	}
	
	public Mushroom() {//constructor vacio de mushroom, que llama al del super por defecto, que ya esta creado
		cambiarNombres(NAME,SHORTCUT);
	}
	
	public Mushroom(GameWorld game, Position pos, Action mov) {//el constructor de Goomba
		super(game, pos);
		cambiarMov(mov);
		cambiarNombres(NAME,SHORTCUT);

	}
	
	@Override
	public boolean interactWith(GameItem other) {//devuelve true si estan en la misma posicion
	     boolean canInteract = this.isInPosition(other);
	     if (canInteract) {
	         other.receiveInteraction(this);
	     }
	     return canInteract;
	}
	
	public GameObject parse (String objWords[], GameWorld game, Position pos) {//devuelve el objeto si el formato es correcto, en caso contrario se devuelve null
		Mushroom c=null;
		if(matchCommandName(objWords[2])) {
			if(objWords.length==4){
				Action mov=devuelveMov(objWords[3]);
				c=new Mushroom(game, pos, mov);
			}
			else if(objWords.length==3)c=new Mushroom(game, pos);
		}
		return c;
	}
	
	
	@Override
	public String getIcon() {//devuelve el icono del mushroom
		return Messages.MUSHROOM;
	}
	
	@Override
	protected Action inicioMov() {//por defecto empieza moviendose hacia la derecha
		return Action.RIGHT;
	}
	
	@Override
	public void update() {//es el movimiento automatico de mushroom
		movimientoAutomatico();
	}
}
