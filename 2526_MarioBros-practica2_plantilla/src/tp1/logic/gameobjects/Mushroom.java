
package tp1.logic.gameobjects;
import java.util.List;

import tp1.exceptions.*;
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
	
	@Override
	protected GameObject clonarEspecifico() {
		Mushroom e=new Mushroom();
		this.guardarMov(e);
		return e;
	}
	
	@Override
	public GameObject parse (String objWords[], GameWorld game, Position pos) throws ObjectParseException, ActionParseException{//devuelve el objeto si el formato es correcto, en caso contrario se devuelve null
		Mushroom c=null;
		if(matchCommandName(objWords[1])&&objWords.length==3) {//si hay 3 palabras y coincide con el objeto
			Action mov=Action.devuelveMov(objWords[2]);
			c=new Mushroom(game, pos, mov);
		}
		else if(matchCommandName(objWords[1])&&objWords.length==2)c=new Mushroom(game, pos);//si coincide y hay dos palabras entonces se crea por defecto
		else if(matchCommandName(objWords[1])&&objWords.length>3)throw new ObjectParseException(Messages.PARSE_INCORRECT_PARAMETER_NUMBER.formatted(String.join(" ", objWords)));//si hay mas palabras de las necesarias, se manda esta excepcion
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
	
	protected void leerEspecifico(List<String> lista) {//se lee lo especifico del objeto
		lista.add(NAME);
		lista.add(" ");
		lista.add(devuelveAction());
	}
	
	@Override
	public void update() {//es el movimiento automatico de mushroom
		movimientoAutomatico();
		if(compararMov(Action.STOP))cambiarMov(inicioMov());//cuando se crea el mushroom porque mario le ha dado a la caja se inicia como stop, pues en la primera vuelta no quiero que se mueva, y así se cambia justo después de no moverse 
	}
}
