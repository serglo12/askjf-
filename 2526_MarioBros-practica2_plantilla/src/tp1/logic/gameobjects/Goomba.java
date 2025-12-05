package tp1.logic.gameobjects;
import tp1.view.Messages;

import java.util.List;

import tp1.exceptions.*;
import tp1.logic.*;

public class Goomba extends MovingObject{
	private static final String NAME =Messages.GOOMBA_NAME;
	private static final String SHORTCUT =Messages.GOOMBA_SHORTCUT;
	
	public Goomba(GameWorld game, Position pos) {//el constructor de Goomba
		super(game, pos);
	}
	
	public Goomba(GameWorld game, Position pos, Action mov) {//el constructor de Goomba en caso de que se le mande un movimiento
		super(game, pos, mov);
	}
	
	public Goomba() {//constructor vacio de goomba, se llama al constructor vacio de gameobject por defecto(esta creado)
		cambiarNombres(NAME,SHORTCUT);
	}

	@Override
	public boolean interactWith(GameItem other) {//devuelve true si estan en la misma posicion, y en ese caso se hacen las interacciones correspondientes
	     boolean canInteract = this.isInPosition(other);
	     if (canInteract) {
	         other.receiveInteraction(this);
	     }
	     return canInteract;
	}
	
	@Override
	public GameObject parse (String objWords[], GameWorld game, Position pos)throws ObjectParseException, ActionParseException{//devuelve el nuevo goomba en caso de ser correcto el formato, y null en otro caso
		Goomba c=null;
		if(matchCommandName(objWords[1])&&objWords.length==3) {//si hay 3 palabras y coincide con el objeto
			Action mov=Action.devuelveMov(objWords[2]);//se lee la posicion(porque deberia de haberla)
			c=new Goomba(game, pos, mov);
		}
		else if(matchCommandName(objWords[1])&&objWords.length==2)c=new Goomba(game, pos);//si no tiene mas argumentos
		else if(matchCommandName(objWords[1])&&objWords.length>3)throw new ObjectParseException(Messages.PARSE_INCORRECT_PARAMETER_NUMBER.formatted((String.join(" ", objWords))));//si hay mas palabras de las necesarias, se manda esta excepcion
		return c;
	}
	
	@Override
	protected GameObject clonarEspecifico() {
		Goomba e=new Goomba();
		this.guardarMov(e);
		return e;
	}
	@Override
	protected Action inicioMov() {//por defecto empieza yendo a la izquierda
		return Action.LEFT;
	}
	
	protected void leerEspecifico(List<String> lista) {//se lee lo especifico del objeto
		lista.add(NAME);
		lista.add(" ");
		lista.add(devuelveAction());
	}

	
	@Override 
	protected void morir() {//al morir se borra el objeto de la lista
		dead();
		game.borrarObject(this);
	}
	@Override
	public  String getIcon() {//devuelve el icono del goomba
		return Messages.GOOMBA;
	}
		
	@Override
	public void update() {//solo hace el movimiento automatico, las interacciones se hacen en gameobjectcontainer
		movimientoAutomatico();
	}
}