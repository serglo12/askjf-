package tp1.logic.gameobjects;
import tp1.view.Messages;

import tp1.logic.*;

public class ExitDoor extends GameObject{
	
	private static final String NAME =Messages.EXITDOOR_NAME;
	private static final String SHORTCUT =Messages.EXITDOOR_SHORTCUT;
	
	public ExitDoor(GameWorld game, Position pos) {//Constructor de la puerta
		super(game, pos);
	}
	
	public ExitDoor() {//constructor vacio, se llama solo al constructor vacio de gameobject
		cambiarNombres(NAME,SHORTCUT);
	}

	@Override
	public boolean isSolid() {//no es solido
		return false;
	}
	
	@Override
	public String getIcon() {//devuelve el icono de la puerta
		return Messages.EXIT_DOOR;	
	}
	
	@Override
	public boolean interactWith(GameItem other) { //devuelve true si esta en la misma posicion que el otro objeto, y en ese caso se hacen las interacciones correspondientes
	     boolean canInteract = this.isInPosition(other);
	     if (canInteract) {
	         other.receiveInteraction(this);
	     }
	     return canInteract;
	}
	@Override
	public GameObject parse (String objWords[], GameWorld game, Position pos) {//si coincide con el nombre o shortcu y despues no hay nada, se crea la puerta correcta, en otro caso devuelve null
		GameObject c=null;
		if(matchCommandName(objWords[2])&&objWords.length==3) {
			c=new ExitDoor(game, pos);
		}
		return c;
	}
}