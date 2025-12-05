package tp1.logic.gameobjects;
import tp1.view.Messages;

import java.util.List;

import tp1.exceptions.ObjectParseException;
import tp1.logic.*;

public class Box extends GameObject{	
	private static final String NAME=Messages.BOX_NAME;
	private static final String SHORTCUT=Messages.BOX_SHORTCUT;
	private static final String BOX_FULL=Messages.BOX_FULL;
	private static final String BOX_FULL_SHORTCUT=Messages.BOX_FULL_SHORTCUT;
	private static final String BOX_EMPTY=Messages.BOX_EMPTY;
	private static final String BOX_EMPTY_SHORTCUT=Messages.BOX_EMPTY_SHORTCUT;
	private boolean full;
	private Position mushroomPos;
	
	public Box(GameWorld game, Position pos) {//constructor de la tierra
		super(game, pos);
		full=true;
		mushroomPos=pos.devolverArriba();//guarda la posicion en la que se tendra que crear el mushroom si mario le da a la caja
	}
	
	public Box() { //constructor vacio de caja, se llama solo al constructor vacio de gameobject
		cambiarNombres(NAME,SHORTCUT);
	}
	@Override
	protected GameObject clonarEspecifico() {
		Box e=new Box();
		e.full=this.full;
		return e;
	}

	@Override
	public boolean interactWith(GameItem other) {//true si estan en la misma posicion, y en ese caso hace las interacciones correspondientes
	     boolean canInteract = this.estaArriba(other);
	     if (canInteract) {
	         other.receiveInteraction(this);
	     }
	     return canInteract;
	}
	
	public void cambiarIcono() {//cambia el icono de la caja
		full=false;
	}
	
	public void sacarMushroom() {//se crea el nuevo mushroom
		if(!mushroomPos.outOfBounds()) {
			GameObject m=new Mushroom(game, mushroomPos, Action.STOP);
			game.addObject(m);
		}
	}
	
	public boolean full() {//devuelve true si tiene un champiñon dentro
		return full;
	}
	
	int devuelveFull(String mov, String objWords[]) throws ObjectParseException{//lee el full de un string
		int full;
		if(mov.equalsIgnoreCase(BOX_FULL)||mov.equalsIgnoreCase(BOX_FULL_SHORTCUT))full=1;
		else if(mov.equalsIgnoreCase(BOX_EMPTY)||mov.equalsIgnoreCase(BOX_EMPTY_SHORTCUT))full=0;
		else {
			full=-1; 
			throw new ObjectParseException(Messages.INVALID_BOX_STATUS.formatted((String.join(" ", objWords))));//se lanza esta excepcion en caso de que no sea correcto el formato
			}
		return full;
	}
	
	private String estadoToString() {//devuelve el string del estado segun este
		if(full)return BOX_FULL;
		else return BOX_EMPTY;
	}
	
	@Override
	public String getIcon() {//devuelve el icono de la caja segun full
		return full ? Messages.BOX : Messages.EMPTY_BOX;
	}
	
	@Override
	public GameObject parse (String objWords[], GameWorld game, Position pos) throws ObjectParseException{ //si coincide con el nombre o shortcut, y despues el formato es correcto(o no hay nada) se crea la caja correcta, en otro caso devuelve null
		Box c=null;
		if(matchCommandName(objWords[1])) {//va probando a ver si es correcto el formato
			if(objWords.length==3){
				int num=devuelveFull(objWords[2], objWords);
				if(num!=-1) {
					c=new Box(game, pos);
					if(num==1)c.full=true;
					else c.full=false;		
				}
			}
			
			else if(objWords.length==2)c=new Box(game, pos);
			else throw new ObjectParseException(Messages.PARSE_INCORRECT_PARAMETER_NUMBER.formatted((String.join(" ", objWords))));//si hay mas palabras de las necesarias, se manda esta excepcion

		}
		return c;
	}
	
	protected void leerEspecifico(List<String> lista) {//anade lo especifico de cada objeto a la lista
		lista.add(NAME);
		lista.add(" ");
		lista.add(estadoToString());
	}
}
	