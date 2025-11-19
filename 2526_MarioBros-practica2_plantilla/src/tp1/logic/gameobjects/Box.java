package tp1.logic.gameobjects;
import tp1.view.Messages;
import tp1.logic.*;

public class Box extends GameObject{	
	private static final String NAME=Messages.BOX_NAME;
	private static final String SHORTCUT=Messages.BOX_SHORTCUT;
	public static final String BOX_FULL="full";
	public static final String BOX_FULL_SHORTCUT="f";
	public static final String BOX_EMPTY="empty";
	public static final String BOX_EMPTY_SHORTCUT="e";
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
		GameObject m=new Mushroom(game, mushroomPos, Action.STOP);
		game.addObject(m);
	}
	
	public boolean full() {//devuelve true si tiene un champiñon dentro
		return full;
	}
	
	int devuelveFull(String mov) {//lee el full de un string
		int full;
		switch(mov) {
		case BOX_FULL:full=1;; break;
		case BOX_FULL_SHORTCUT: full=1; break;
		case BOX_EMPTY_SHORTCUT: full=0; break;
		case BOX_EMPTY: full=0; break;
		default: full=-1; break;
		}
		return full;
	}
	
	@Override
	public String getIcon() {//devuelve el icono de la caja segun full
		return full ? Messages.BOX : Messages.EMPTY_BOX;
	}
	
	@Override
	public GameObject parse (String objWords[], GameWorld game, Position pos) { //si coincide con el nombre o shortcut, y despues el formato es correcto(o no hay nada) se crea la caja correcta, en otro caso devuelve null
		Box c=null;
		if(matchCommandName(objWords[2])) {
			if(objWords.length==4){
				int num=devuelveFull(objWords[3]);
				if(devuelveFull(objWords[3])!=-1) {
					c=new Box(game, pos);
					if(num==1)c.full=true;
					else c.full=false;		
				}
			}
			
			else if(objWords.length==3)c=new Box(game, pos);
		}
		return c;
	}
	
	
}