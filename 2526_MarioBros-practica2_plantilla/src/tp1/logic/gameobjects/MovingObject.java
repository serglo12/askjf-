package tp1.logic.gameobjects;

import tp1.logic.*;


public abstract class MovingObject extends GameObject{
	
	private Action mov;//Enum p�blico de game con el que guardar el estado de Movimiento actual
	
	public MovingObject(GameWorld game, Position pos) {//constructor de movingobject
		super(game, pos);
		mov=inicioMov();
	}
	
	public MovingObject(GameWorld game, Position pos, Action mov2) {//constructor para addObjectCommand en caso de que se le mande un movimiento
		super(game, pos);
		this.mov=mov2;
	}
	
	boolean puedoDerecha() {//true si puedo moverme a la derecha
		return !getPosition().devolverDerecha().outOfBounds()&&!game.isSolid(getPosition().devolverDerecha());
	}
	
	boolean abajoFueraDeTablero() {//true si abajo esta fuera de tablero
		return getPosition().devolverAbajo().outOfBounds();
	}
	
	boolean puedoIzquierda() {//true si puedo moverme a la izquierda
		return !getPosition().devolverIzquierda().outOfBounds()&&!game.isSolid(getPosition().devolverIzquierda());
	}
	
	boolean puedoAbajo() {//true si puedo moverme abajo
		return !getPosition().devolverAbajo().outOfBounds()&&!game.isSolid(getPosition().devolverAbajo());
	}
	
	boolean puedoArriba() {//true si puedo moverme arriba
		return !getPosition().devolverArriba().outOfBounds()&&!game.isSolid(getPosition().devolverArriba());
	}
	
	boolean puedoArribaDerecha() {//true si puedo moverme arriba a la derecha
		return !getPosition().devolverDerecha().devolverArriba().outOfBounds()&&!game.isSolid(getPosition().devolverDerecha().devolverArriba());
	}
	
	boolean puedoArribaIzquierda() {//true si puedo moverme arriba a la izquierda
		return !getPosition().devolverIzquierda().devolverArriba().outOfBounds()&&!game.isSolid(getPosition().devolverIzquierda().devolverArriba());
	}
	
	boolean puedoArribaArriba() {//true si puedo moverme dos casillas arriba
		return !getPosition().devolverArriba().devolverArriba().outOfBounds()&&!game.isSolid(getPosition().devolverArriba().devolverArriba());
	}
	
	public MovingObject() {//constructor vacio, para addobjectcommand, es el que se llama por defecto en sus clases hijas
	}
	
	Action getMov() {
		return this.mov;
	}
	
	protected abstract Action inicioMov();//cada uno tiene un movimiento distinto

	@Override
	public boolean isSolid() {//todos los objetos que se mueven son solidos
		return false;
	}
	
	boolean isUp() {//true si se esta moviendo hacia arriba
		return mov==Action.UP;
	}
	
	boolean compararMov(Action mov2) {//compara el atributo con el movimiento actual
		return mov2==this.mov;
	}
	
	protected String devuelveAction() {
		return Action.devuelveString(mov);
	}
	
	void cambiarMov(Action mov2) {//cambia el movimiento actual por el atributo
		this.mov=mov2;
	}


	boolean isFalling() { //devuelve true si esta cayendo
		return mov==Action.DOWN;
	}
	
	protected void guardarMov(MovingObject e) {
		e.mov=this.mov;
	}
	
	private void moverIzquierda() {//se mueve a la izquierda
		setPosition(getPosition().devolverIzquierda());
		cambiarMov(Action.LEFT);
	}
	
	protected void moverAbajo() {//se mueve abajo
		setPosition(getPosition().devolverAbajo());
	}
	
	protected void LEFT() {//toda la logica para moverse hacia la izquierda, si no hay nada, se mueve normal, y si hay algo y tiene el icono de movimiento hacia la izquierda, se cambia al icono de movimiento hacia la derecha. Ademas, se tiene en cuenta la cabeza cuando es grande a la hora de moverse
		if(puedoIzquierda()) {// si la izquierda no esta fuera de tablero y no hay tierra
			moverIzquierda();
		}
		else cambiarMov(Action.RIGHT);
	}
	
	
	private void moverDerecha() {//se mueve a la derecha
		setPosition(getPosition().devolverDerecha());
		cambiarMov(Action.RIGHT);
	}
	
	protected void RIGHT() {//toda la logica para moverse hacia la derecha, si no hay nada, se mueve normal, y si hay algo y tiene el icono de movimiento hacia la derecha, se cambia al icono de movimiento hacia la izquierda. Ademas, se tiene en cuenta la cabeza cuando es grande a la hora de moverse
		if(puedoDerecha()) {// si la izquierda no esta fuera de tablero y no hay tierra
			moverDerecha();
		}
		else {
			cambiarMov(Action.LEFT);
		}
	}
	
	protected void morir(){//se muere el objeto y se borra de la lista
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
