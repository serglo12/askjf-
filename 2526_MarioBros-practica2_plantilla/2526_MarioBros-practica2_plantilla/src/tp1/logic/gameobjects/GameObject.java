package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;

public abstract class GameObject implements GameItem{ // TODO 
	
	
	private Position pos;
	private boolean isAlive;
	protected GameWorld game; 
	private boolean isSolid;
	private Position preUpdateMov;
	private String name;
	private String shortcut;
	
	

	public GameObject(GameWorld game, Position pos) {//constructor general de cualquier objeto
		this.isAlive = true;
		this.pos = pos;
		this.game = game;
		isSolid=isSolid();//devuelve true o false segun cual sea el objeto
	}
	public GameObject() {}//constructor vacio del objeto
	
	public abstract GameObject parse (String objWords[], GameWorld game, Position pos);//funcion abstracta que crea el objeto si el formato es correcto
	
	void cambiarNombres(String NAME, String SHORTCUT){//cambia el nombre del objeto a los atributos
		name=NAME;
		shortcut=SHORTCUT;
	}
	
	protected boolean matchCommandName(String name) {//true si coincide con el nombre o el shortcut, dando igual mayusculas o minusculas
		return this.name.equalsIgnoreCase(name) || this.shortcut.equalsIgnoreCase(name);
	}
	
	public void update() {// vacio y no abstracto para que en el caso de los solidos no haya que crear un update en su clase
	}
	
	public void addObject(GameObject obj) {//añade un objeto a la lista de objetos
		game.addObject(obj);
	}
	
	public boolean isSolid(){//devuelve true si el objeto es solido
		return isSolid;
	}
	
	public void revivir() {//revive al objeto
		isAlive=true;
	}
	
	void guardarPosPreUpdate() {//guarda la posicion previa a hacer su update
		preUpdateMov=this.pos;
	}
	
	boolean sigueIgual() {//compara si el objeto sigue igual que antes de hacer el update
		return this.pos.comparePos(preUpdateMov);
	}
	
	void derecha() {//se mueve a la derecha
		this.pos=this.pos.devolverDerecha();
	}
	
	void izquierda() {//se mueve a la izquierda
		this.pos=this.pos.devolverIzquierda();
	}
	
	void arriba() {//se mueve hacia arriba
		this.pos=this.pos.devolverArriba();
	}
	
	void abajo() {//se mueve hacia abajo
		this.pos=this.pos.devolverAbajo();
	}
	
	boolean puedoDerecha() {//true si puedo moverme a la derecha
		return !this.pos.devolverDerecha().outOfBounds()&&!game.isSolid(this.pos.devolverDerecha());
	}
	
	boolean abajoFueraDeTablero() {//true si abajo esta fuera de tablero
		return this.pos.devolverAbajo().outOfBounds();
	}
	
	boolean puedoIzquierda() {//true si puedo moverme a la izquierda
		return !this.pos.devolverIzquierda().outOfBounds()&&!game.isSolid(this.pos.devolverIzquierda());
	}
	
	boolean puedoAbajo() {//true si puedo moverme abajo
		return !this.pos.devolverAbajo().outOfBounds()&&!game.isSolid(this.pos.devolverAbajo());
	}
	
	boolean puedoArriba() {//true si puedo moverme arriba
		return !this.pos.devolverArriba().outOfBounds()&&!game.isSolid(this.pos.devolverArriba());
	}
	
	boolean puedoArribaDerecha() {//true si puedo moverme arriba a la derecha
		return !this.pos.devolverDerecha().devolverArriba().outOfBounds()&&!game.isSolid(this.pos.devolverDerecha().devolverArriba());
	}
	
	boolean puedoArribaIzquierda() {//true si puedo moverme arriba a la izquierda
		return !this.pos.devolverIzquierda().devolverArriba().outOfBounds()&&!game.isSolid(this.pos.devolverIzquierda().devolverArriba());
	}
	
	boolean puedoArribaArriba() {//true si puedo moverme dos casillas arriba
		return !this.pos.devolverArriba().devolverArriba().outOfBounds()&&!game.isSolid(this.pos.devolverArriba().devolverArriba());
	}
	
	public boolean isInPosition(GameItem obj) {//true si estan en la misma posicion
		return obj.isInPosition(this.pos);
	}
	
	public boolean isInPosition(Position pos) {//true si estan en la  misma posicion
		return this.pos.comparePos(pos);
	}
	
	public boolean estaArriba(GameItem obj) {//true si esta arriba
		return obj.isInPosition(this.pos.devolverAbajo());
	}
 	
	public boolean isAlive() {//devuelve true si el objeto esta vivo
		return isAlive;
	}
	
	protected void dead(){   //pone al objeto como muerto
		this.isAlive = false;
	}
	
	//todas estas interacciones devuelve falso, y en caso de que dos objetos interactuen se sobreescribe el metodo dentro de la propia clase
	
	public boolean receiveInteraction(Land obj) {
		return false;
	}
	
	public boolean receiveInteraction(Mario obj) {
		return false;
	}
	
	public boolean receiveInteraction(Goomba obj) {
		return false;
	}
	
	public boolean receiveInteraction(ExitDoor obj) {
		return false;
	}
	
	public boolean receiveInteraction(Mushroom obj) {
		return false;
	}
	
	public boolean receiveInteraction(Box obj) {
		return false;
	}
	
	public boolean interactWith(GameItem other) {
	     return false;
	}	
	
	public abstract String getIcon();

	// Not mandatory but recommended
	protected void move(Action dir) {
		// TODO Auto-generated method stub
	}
}
