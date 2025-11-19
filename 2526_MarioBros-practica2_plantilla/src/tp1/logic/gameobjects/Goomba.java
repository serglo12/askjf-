package tp1.logic.gameobjects;
import tp1.view.Messages;
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
	public GameObject parse (String objWords[], GameWorld game, Position pos) {//devuelve el nuevo goomba en caso de ser correcto el formato, y null en otro caso
		Goomba c=null;
		if(matchCommandName(objWords[2])) {
			if(objWords.length==4){
				Action mov=devuelveMov(objWords[3]);
				if(mov!=null)c=new Goomba(game, pos, mov);
			}
			else if(objWords.length==3)c=new Goomba(game, pos);
		}
		return c;
	}
	
	@Override
	protected Action inicioMov() {//por defecto empieza yendo a la izquierda
		return Action.LEFT;
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