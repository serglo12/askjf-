package tp1.logic.gameobjects;

import tp1.logic.GameWorld;

import java.util.List;
import tp1.exceptions.*;
import tp1.logic.Position;

public abstract class GameObject implements GameItem{ // TODO 
	
	
	private Position pos;
	private boolean isAlive;
	protected GameWorld game; 
	private String name;
	private String shortcut;
	
	public GameObject(GameWorld game, Position pos) {//constructor general de cualquier objeto
		this.isAlive = true;
		this.pos = pos;
		this.game = game;
	}
	public GameObject() {}//constructor vacio del objeto
	
	public abstract GameObject parse (String objWords[], GameWorld game, Position pos)throws OffBoardException, GameParseException;//funcion abstracta que crea el objeto si el formato es correcto
	
	void cambiarNombres(String NAME, String SHORTCUT){//cambia el nombre del objeto a los atributos
		name=NAME;
		shortcut=SHORTCUT;
	}
	
	public void saveObject(List<String> lista) {//salva el objeto correspondiente
		lista.add(pos.devolverPosition());
		lista.add(" ");
		leerEspecifico(lista);
		lista.add("\n");
	}
	
	protected abstract void leerEspecifico(List<String> lista);//cada objeto tiene sus cosas particulares, principalmente el nombre
	
	public boolean matchCommandName(String name) {//true si coincide con el nombre o el shortcut, dando igual mayusculas o minusculas
		return this.name.equalsIgnoreCase(name) || this.shortcut.equalsIgnoreCase(name);
	}
	
	public void update() {// vacio y no abstracto para que en el caso de los solidos no haya que crear un update en su clase
	}
	
	public GameObject clonarObject() {
		GameObject e=clonarEspecifico();
		e.pos=this.pos;
		e.game=this.game;
		e.isAlive=this.isAlive;
		return e;
	}

	protected abstract GameObject clonarEspecifico();
	
	public void addObject(GameObject obj) {//añade un objeto a la lista de objetos
		game.addObject(obj);
	}
	@Override
	public boolean isSolid() {
		return true;
	}//devuelve true si el objeto es solido
	
	public void revivir() {//revive al objeto
		isAlive=true;
	}
	
	void setPosition(Position pos) {//se cambia la posicion a donde se le diga
		this.pos=pos;
	}
	
	Position getPosition() {
		return this.pos;
	}
	
	public boolean isInPosition(GameItem obj) {//true si estan en la misma posicion
		return obj.isInPosition(this.pos);
	}
	@Override
	public boolean isInPosition(Position pos) {//true si estan en la  misma posicion
		return this.pos.comparePos(pos);
	}
	
	public boolean estaArriba(GameItem obj) {//true si esta arriba
		return obj.isInPosition(this.pos.devolverAbajo());
	}
 	
	@Override
	public boolean isAlive() {//devuelve true si el objeto esta vivo
		return isAlive;
	}
	
	protected void dead(){   //pone al objeto como muerto
		this.isAlive = false;
	}
	
	//todas estas interacciones devuelve falso, y en caso de que dos objetos interactuen se sobreescribe el metodo dentro de la propia clase
	@Override
	public boolean receiveInteraction(Land obj) {
		return false;
	}
	@Override
	public boolean receiveInteraction(Mario obj) {
		return false;
	}
	@Override
	public boolean receiveInteraction(Goomba obj) {
		return false;
	}
	@Override
	public boolean receiveInteraction(ExitDoor obj) {
		return false;
	}
	@Override
	public boolean receiveInteraction(Mushroom obj) {
		return false;
	}
	@Override
	public boolean receiveInteraction(Box obj) {
		return false;
	}
	@Override
	public boolean interactWith(GameItem other) {
	     return false;
	}	
	
	public abstract String getIcon();
}
