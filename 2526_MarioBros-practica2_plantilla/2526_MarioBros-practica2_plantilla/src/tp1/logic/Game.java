package tp1.logic;
import tp1.logic.gameobjects.*;

public class Game implements GameModel, GameWorld, GameStatus{

	public static final int DIM_X = 30;//dimensiones del tablero
	public static final int DIM_Y = 15;
	
	private int nLevel;
	private int points;
	private int remainingTime;
	private int lives;
	private Mario mario;
	private boolean win;//true si ha mario ha ganado
	private boolean exitComando;//true si se le ha dado al comando de exit
	private GameObjectContainer gameObjects=new GameObjectContainer();//Objecto de GameObjectContainer donde se guardan todos los elementos del juego.
	
	public Game(int nLevel) {//Constructor de Game
		this.nLevel= nLevel;
		this.points=0;
		this.lives=3;
		this.remainingTime=100;
		this.exitComando=false;
		this.win=false;
		reset(nLevel);//se inicia el nivel que uno quiero, se puede cambiar al 1 
	}
	
	//Métodos de GameModel
	public void update() {//actualiza el juego y resta 1 segundo al tiempo por cada actualizaci�n
		--this.remainingTime;
		gameObjects.update();
	}
	
	public void reset(int nuevoNumLevel) {//reset cuando se manda un nivel como argumento
		this.nLevel=nuevoNumLevel;
		if(nuevoNumLevel==0) this.initLevel0();
		else if(nuevoNumLevel==1)this.initLevel1();
		else if(nuevoNumLevel==-1)this.initLevel_1();
		else this.initLevel2();
	}
	
	public void reset(){//reset sin argumento(se reinicia en el nivel en el que est�s)
		if(this.nLevel==0)this.initLevel0();
		else if(this.nLevel==1)this.initLevel1();
		else if(this.nLevel==-1)this.initLevel_1();
		else this.initLevel2();
	}
	
	public void exit() {//se le ha dado al comando de exit
		exitComando=true;
	}
	
	public boolean addObject(String[] objWords) { //se utiliza para el addObjectCommand, que dado todo el array de palabras, gameobjectfactory devuelve el objecto correcto
		GameObject obj=GameObjectFactory.parse(objWords, this);
		boolean n=false;
		if(obj!=null) { //si no es nulo se añade a la lista de gameobjects
			gameObjects.add(obj);
			n=true;
		}
		return n;//devuelve true si el objeto no es nulo
	}

	public boolean isFinished() {//devuelve true si el juego ha terminado para as� terminar el bucle de controller
		return win||this.playerLoses()||exitComando;
	}
	
	public void addAction(Action action) {//a�ade una acci�n a la lista
		mario.addAction(action);
	}
	
	//Métodos de GameWorld
	public String positionToString(Position pos) {//devuelve lo que haya pintado en esa posici�n
		return gameObjects.positionToString(pos);
	}
	
	public void borrarObject(GameObject obj) {//se borra el objeto de la lista
		gameObjects.borrarObject(obj);
	}

	public boolean marioHaPerdido() {//devuelve true si mario ha perdido, solo sirve para que, en caso de ganar, si muestre el tablero(si ha muerto o se le ha dado a exit no lo hace)
		return this.playerLoses()||exitComando;
	}
	
	public void marioExited() {//se llama si mario ha tocado la puerta, y se suman los puntos correspondientes.
		this.points=this.points+this.remainingTime*10;
		win=true;
	}
	
	public void doInteractionsFrom(GameObject object) {
		gameObjects.doInteractionsFrom(object);
	}
	
	public void puntosGoombaMuerto() {//se suman los puntos por haber matado a un goomba.
		this.points+=100;
	}
	
	public void puntosBox() { //se suma los puntos por haberle dado a una caja
		this.points+=50;
	}
	
	public void addObject(GameObject obj) { //se añade el objeto correspondiente(ahora mismo solo vale para añadir el champiñon después de darle a la caja
		gameObjects.add(obj);
	}
	
	public void actualizarMario(Mario mario2) { //si se añade un mario con addObject, el que se controla es el nuevo
		this.mario=mario2;
	}

	
	public void restarVida() {//se resta una vida
		this.lives--;
	}
	
	public boolean isSolid(Position pos) {//true si hay solido en esa posicion
		return gameObjects.isSolid(pos);
	}
	
	public void marioHaMuerto() { //resta vida y resetea el juego
		restarVida();
		reset();
	}


	//Métodos de GameStatus
	
	public void initLevel0() {//inicia el nivel 0
		this.nLevel = 0;
		this.remainingTime = 100;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(this, new Position(13,col)));
			gameObjects.add(new Land(this, new Position(14,col)));		
		}

		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(this,new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(this,new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(this,new Position(9,2)));
		gameObjects.add(new Land(this,new Position(9,5)));
		gameObjects.add(new Land(this,new Position(9,6)));
		gameObjects.add(new Land(this,new Position(9,7)));
		gameObjects.add(new Land(this,new Position(5,6)));
		
		// Salto final
		int tamX = 8, tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(this,new Position(posIniY- fila, posIniX+ col)));
			}
		}
		
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));
		gameObjects.add(this.mario);
		
		gameObjects.add(new ExitDoor(this,new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes


		gameObjects.add(new Goomba(this, new Position(0, 19)));

	}
	
	
	public void initLevel1() {//inicia el nivel 1 
		this.initLevel0();
		
		this.nLevel=1;
		gameObjects.add(new Goomba(this, new Position(4, 6)));
		gameObjects.add(new Goomba(this, new Position(12, 6)));
		gameObjects.add(new Goomba(this, new Position(10, 10)));
		gameObjects.add(new Goomba(this, new Position(12, 11)));
		gameObjects.add(new Goomba(this, new Position(12, 14)));
		gameObjects.add(new Goomba(this, new Position(12, 8)));



	}
	
	public void initLevel2() {//inicia el nivel 2
		this.initLevel1();
		this.nLevel=2;
		
		gameObjects.add(new Box(this, new Position(9,4)));
		gameObjects.add(new Mushroom(this, new Position(12,8)));
		gameObjects.add(new Mushroom(this, new Position(2,20)));


		
	}
	
	public void initLevel_1() { //inicia el nivel -1, que es el tablero vacio
		gameObjects = new GameObjectContainer();

		this.nLevel = -1;
		this.remainingTime = 100;
		this.points=0;
		this.lives=0;
		
	}
	
	public String positionToString(int col, int row) {//esta funci�n esta dentro de un bucle que genera todo el tablero
		Position pos=new Position(row, col);	
		return gameObjects.positionToString(pos);
	}
	
	public int remainingTime() {//devuelve el tiempo que queda
		return this.remainingTime;
	}

	public int points() {//devuelve los puntos
		return this.points;
	}
	
	public int numLives() {//devuelve las vidas
		return this.lives;
	}
	
	public boolean playerLoses() {//true si se han acabado las vidas o el tiempo
		return this.lives==0||this.remainingTime==0;
	}
	
	public boolean playerWins() {//devuelve true si ha ganado
		return win;
	}
		
	@Override
	public String toString() {
		// TODO returns a textual representation of the object
		return "TODO: Hola soy el game";
	}
}
