package tp1.logic.gameobjects;

import tp1.logic.*;
import tp1.view.Messages;;


public abstract class MovingObject extends GameObject{
	
	private Action mov;//Enum p�blico de game con el que guardar el estado de Movimiento actual
	private boolean big;
	private Action preUpdateMov;
	protected static final String left=Messages.LEFT;
	protected static final String left_shortcut=Messages.LEFT_SHORTCUT;
	protected static final String right= Messages.RIGHT;
	protected static final String right_shortcut=Messages.RIGHT_SHORTCUT;
	
	public MovingObject(GameWorld game, Position pos) {//constructor de movingobject
		super(game, pos);
		big=false;
		mov=inicioMov();
	}
	
	public MovingObject(GameWorld game, Position pos, Action mov2) {//constructor para addObjectCommand en caso de que se le mande un movimiento
		super(game, pos);
		big=false;
		this.mov=mov2;
	}
	
	public MovingObject() {//constructor vacio, para addobjectcommand, es el que se llama por defecto en sus clases hijas
	}
	
	
	Action devuelveMov(String mov) {//pasa el string de movimiento al action correspondiente 
		Action mov2;
		mov.toLowerCase();
		switch(mov) {
		case left:mov2=Action.LEFT; break;
		case left_shortcut: mov2=Action.LEFT; break;
		case right: mov2=Action.RIGHT; break;
		case right_shortcut: mov2=Action.RIGHT; break;
		default: mov2=null; break;
		}
		return mov2;
	}
	
	protected abstract Action inicioMov();//cada uno tiene un movimiento distinto

	@Override
	public boolean isSolid() {//todos los objetos que se mueven son solidos
		return false;
	}
	
	protected boolean isUp() {//true si se esta moviendo hacia arriba
		return mov==Action.UP;
	}
	
	public boolean big() {//devuelve el atributo de big
		return big;
	}
		
	public void cambiarTamano() {//cambia el tamano
		big=!big;
	}
	
	boolean compararMov(Action mov2) {//compara el atributo con el movimiento actual
		return mov2==this.mov;
	}
	
	void cambiarMov(Action mov2) {//cambia el movimiento actual por el atributo
		this.mov=mov2;
	}

	@Override
	boolean sigueIgual() {//true si sigue igual
		return this.mov==this.preUpdateMov&&super.sigueIgual();
	}
	
	void guardarPreUpdateMov() {//guarda el movimiento previo al update
		this.preUpdateMov=this.mov;
	}

	public boolean isFalling() { //devuelve true si esta cayendo
		return mov==Action.DOWN;
	}
	
	private void moverIzquierda() {//se mueve a la izquierda
		izquierda();
		cambiarMov(Action.LEFT);

	}
	
	protected void moverAbajo() {//se mueve abajo
		abajo();
	}
	
	protected void LEFT() {//toda la logica para moverse hacia la izquierda, si no hay nada, se mueve normal, y si hay algo y tiene el icono de movimiento hacia la izquierda, se cambia al icono de movimiento hacia la derecha. Ademas, se tiene en cuenta la cabeza cuando es grande a la hora de moverse
		if(puedoIzquierda()) {// si la izquierda no esta fuera de tablero y no hay tierra
			if(big()&&puedoArribaIzquierda()) {
				moverIzquierda();
			}
			else if(!big()) {
				moverIzquierda();
			}
			else cambiarMov(Action.RIGHT);
		}
		else {
			cambiarMov(Action.RIGHT);
		}
	}
	
	
	private void moverDerecha() {//se mueve a la derecha
		derecha();
		cambiarMov(Action.RIGHT);
	}
	
	protected void RIGHT() {//toda la logica para moverse hacia la derecha, si no hay nada, se mueve normal, y si hay algo y tiene el icono de movimiento hacia la derecha, se cambia al icono de movimiento hacia la izquierda. Ademas, se tiene en cuenta la cabeza cuando es grande a la hora de moverse
		if(puedoDerecha()) {// si la izquierda no esta fuera de tablero y no hay tierra
			if(big()&&puedoArribaDerecha()) {
				moverDerecha();
			}
			else if(!big()) {
				moverDerecha();
			}
			else cambiarMov(Action.LEFT);
		}
		else {
			cambiarMov(Action.LEFT);
		}
	}
	
	protected void morir() {//se muere el objeto y se borra de la lista
		dead();
		game.borrarObject(this);
	}
	
	
	protected void movimientoAutomatico() {//el movimiento automatico
		if(!puedoAbajo()&&!abajoFueraDeTablero()) { //si abajo no esta fuera de tablero y hay tierra 
			//la l�gica va en funci�n de su icono actual
			if(compararMov(Action.LEFT)) {
				this.LEFT();
			}
			else if(compararMov(Action.RIGHT)){
				this.RIGHT();
			}
		}
		else if(abajoFueraDeTablero()){// si abajo est� fuera de tablero se borra de la lista
			morir();
		}
		else {//si abajo no est� fuera de tablero y no hay tierra, desciende una posici�n
			moverAbajo();
		}
	}
}
