package tp1.logic.gameobjects;
import tp1.view.Messages;

import java.util.List;

import tp1.exceptions.ObjectParseException;
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
	public GameObject parse (String objWords[], GameWorld game, Position pos) throws ObjectParseException{//si coincide con el nombre o shortcu y despues no hay nada, se crea la puerta correcta, en otro caso devuelve null
		GameObject c=null;
		if(matchCommandName(objWords[1])&&objWords.length==2) {//si coincide con el nombre y tiene dos elementos es correcto
			c=new ExitDoor(game, pos);
		}
		else if(matchCommandName(objWords[1])) throw new ObjectParseException(Messages.PARSE_INCORRECT_PARAMETER_NUMBER.formatted((String.join(" ",objWords))));//si hay mas palabras de las necesarias, se manda esta excepcion
		return c;
	}
	
	protected void leerEspecifico(List<String> lista) {//anade lo especifico de cada objeto a la lista
		lista.add(NAME);
	}
	@Override
	protected GameObject clonarEspecifico() {
		ExitDoor e=new ExitDoor();
		return e;
	}
}