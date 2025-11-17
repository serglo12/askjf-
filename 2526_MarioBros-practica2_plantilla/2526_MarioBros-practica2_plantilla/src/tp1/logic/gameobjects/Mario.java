package tp1.logic.gameobjects;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.*;
import tp1.view.Messages;

public class Mario extends MovingObject{

	private ActionList actionList;//Objeto de actionList para poder llamar a la funci�n que arregla la lista de acciones
	private List<Action> actionListToDo;//lista de acciones correctas
	private static final String NAME = Messages.MARIO_NAME;
	private static final String SHORTCUT =Messages.MARIO_SHORTCUT;
	private static final String big=Messages.BIG;
	private static final String big_shortcut=Messages.BIG_SHORTCUT;
	private static final String small=Messages.SMALL;
	private static final String small_shortcut=Messages.SMALL_SHORTCUT;
	private String iconoActual; //se guarda el icono 

	
	public Mario(GameWorld game, Position pos) {//el constructor del objeto Mario
		super(game, pos);
		iconoActual=this.getIcon();
		cambiarTamano();//empieza grande
		actionList=new ActionList();
		actionListToDo= new ArrayList<>();
	}
	
	public Mario(GameWorld game, Position pos, Action mov) {//el constructor del objeto Mario
		super(game, pos, mov);
		iconoActual=this.getIcon();
		cambiarTamano();//empieza grande
		actionList=new ActionList();
		actionListToDo= new ArrayList<>();
	}
	
	public Mario() {
		cambiarNombres(NAME,SHORTCUT);
	}
	
	int leerTamano(String s) {
		int tam;
		s.toLowerCase();
		switch(s) {
		case big:tam=1;; break;
		case big_shortcut: tam=1; break;
		case small_shortcut: tam=0; break;
		case small: tam=0; break;
		default: tam=-1; break;
		}
		return tam;
	}

	
	public GameObject parse (String objWords[], GameWorld game, Position pos) {//devuelve el nuevo Mario en caso de que el formato sea correcto, en otro caso devuelve null, 
		//además, el nuevo mario creado es el que se guarda en game, es decir, es el que se controla  con las acciones, el otro mario se quedaria haciendo el movimiento automatico como un objeto más
		Mario c=null;
		if(matchCommandName(objWords[2])) {
			if(objWords.length>=4){
				Action mov=devuelveMov(objWords[3]);
				if(mov!=null&&objWords.length==4) {
					c=new Mario(game, pos, mov);
					game.actualizarMario(c);
				}
				else if(mov!=null&&objWords.length==5) {
					int n=leerTamano(objWords[4]);
					if(n!=-1) {
						c=new Mario(game, pos, mov);
						if(n==0)c.cambiarTamano();
						game.actualizarMario(c);
					}
				}	
			}
			else if(objWords.length==3){
				c=new Mario(game, pos);
				game.actualizarMario(c);
			}
		}
		return c;
	}
	

	@Override
	public boolean interactWith(GameItem other) {//devuelve true si esta en la misma posicion y esta vivo, y en ese caso hace las interacciones correspondientes
	     boolean canInteract = this.isInPosition(other);
	     if (canInteract&&isAlive())other.receiveInteraction(this);
	     return canInteract&&isAlive();
	}
	
	@Override
	public boolean receiveInteraction(Mushroom obj) {//si toca al mushroom y es pequeño, mario se hace grande, en otro caso no se hace nada, y en cualquier caso se borra el mushroom
		obj.morir();
		if(!big())cambiarTamano();
		return true;
	}
	
	
	@Override
	public boolean receiveInteraction(Goomba obj) {/*las interacciones con goomba, si mario esta muerto no se hacen, y si esta vivo: si mario isFalling, mata al goomba;
		en otro caso si es grande se hace peque�o y mata al goomba, y si no es grande se reseta el juego y mario se muere. como alguien muere siempre, siempre devuelve true*/
		if(!this.isFalling()) {
			if(big()) {
				cambiarTamano();
			}
			else {
				morir();
			}
		}
		game.puntosGoombaMuerto();
		obj.morir();
		return true; //siempre interactúa
	}
	
	@Override
	public boolean receiveInteraction(ExitDoor obj) {//comprueba si mario ha interactuado con la puerta, si es asi, se llama a marioExited de game
		game.marioExited();
		this.dead();
		return true;
	}
	
	public boolean receiveInteraction(Box obj) {//comprueba si mario ha interactuado con la caja, si mario esta subiendo y la caja esta llena, entonces se anaden los puntos, se cambia el icono y sale el mushroom
		if(isUp()&&obj.full()) {
			game.puntosBox();
			obj.cambiarIcono();
			obj.sacarMushroom();
		}
		return true;
	}
	
	protected Action inicioMov() {//empieza por la derecha por defecto
		return Action.RIGHT;
	}
	
	@Override
	public String getIcon() {//esta funcion devuelve el icono actual de mario en funcion de cual sea su estado de movimiento
		String a=iconoActual;
		if(compararMov(Action.RIGHT))a=Messages.MARIO_RIGHT;
		else if(compararMov(Action.LEFT))a=Messages.MARIO_LEFT;
		else if(compararMov(Action.STOP))a=Messages.MARIO_STOP;
		iconoActual=a;
		return a;
	}
	
	
	public void addAction(Action action) {//Se anade una accion a la lista de acciones
		actionList.addAction(action);
	}

	private void listaDeAcciones() {//se guarda en actionListToDo la lista de acciones arreglada, dentro de la cual hay que realizar todos esos movimientos 
		this.actionListToDo=this.actionList.listaDeAcciones();
	}
	
	private void iconToAction() {//si mario baja o sube, para que siga moviendose a la derecha con el movimiento automatico, despues de hacer las interacciones se cambia el action segun cual sea su icono
		switch (iconoActual) {
		case Messages.MARIO_RIGHT: cambiarMov(Action.RIGHT); break;
		case Messages.MARIO_LEFT: cambiarMov(Action.LEFT); break;
		default: break;
		}
	}
	
	
	@Override
	public void update() {//aqui se realizan todas las acciones de mario, ademas de comprobar las interacciones de mario despues de cada movimiento
		this.listaDeAcciones();
		guardarPosPreUpdate();
		guardarPreUpdateMov();
		for(int i=0; i<actionListToDo.size();++i) {
			switch(actionListToDo.get(i)) {
			case LEFT: this.LEFT();
			break;
			case RIGHT: this.RIGHT();
			break;
			case UP: this.UP(); 
			break;
			case DOWN: this.DOWN();
			break;
			default: this.STOP();
			break;
			}
		game.doInteractionsFrom(this);
		}
		if((this.sigueIgual()&&!compararMov(Action.STOP))||actionListToDo.size()==0) {//si el resultado de todas las acciones es que se queda en la misma posicion y no ha cambiado su direccion de movimiento, se ejecuta el movimiento automatica. solo hace el movimiento automatico no esta parado
			movimientoAutomatico();
			game.doInteractionsFrom(this);
		}
		if(compararMov(Action.DOWN)||compararMov(Action.UP))iconToAction();//solo se hace iconToAction si action es UP o DOWN
		actionListToDo.clear();//reiniciamos la lista
	}
	
	private void UP() {//toda la logica de mario cuando quiere moverse hacia arriba, si es grande pues la posicion a tener en cuenta es la de su cabeza, y si arriba es tierra o fuera de tablero, no se mueve.
		if(!big()) {
			if(puedoArriba()) {
				moverArriba();
			}
		}
		else {
			if(puedoArriba()&&puedoArribaArriba()) {
				moverArriba();
			}	
		}
	}
	
	private void moverArriba() {//se mueve hacia arriba y cambia el action
		arriba();
		cambiarMov(Action.UP);
	}
	
	@Override
	protected void moverAbajo() {//sobreescrito porque para el resto de movingObjects no queremos que cambie su movimiento a down
		abajo();
		cambiarMov(Action.DOWN);
	}
	
	@Override
	protected void morir() {//se muere y avisa a game de que esta muerto
		dead();
		game.marioHaMuerto();
	}
	
	
	private void DOWN() {/*la logica de mario cuando quiere moverse hacia abajo, hay que tener en cuenta las interacciones con el resto de objetos despues del movimiento en cada iteracion del bucle.
	Y si se hace en el suelo cambia el estado a parado. 
		*/
		while(puedoAbajo()) {
			moverAbajo();
			game.doInteractionsFrom(this);
		}
		
		if(abajoFueraDeTablero()) {
			morir();
		}
		else if(!puedoAbajo()&&!isFalling())this.STOP();
	}
	
	public void STOP() {//toda la logica de mario cuando se le da a Stop, solo cambia su icono a parado.
		cambiarMov(Action.STOP);
	}
	
	@Override
	public boolean isInPosition(Position pos2) {//devuelve true si la posicion recibida coincide con la de mario, y si es grande, si coindice con la de la cabeza tambien es true
		boolean n=false;
		if(big()&&super.isInPosition(pos2.devolverAbajo()))n=true;
		return super.isInPosition(pos2)||n;
	}
}