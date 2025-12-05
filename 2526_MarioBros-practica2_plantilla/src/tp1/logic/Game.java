package tp1.logic;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import tp1.exceptions.*;

public class Game implements GameModel, GameWorld, GameStatus{

	public static final int DIM_X = 30;//dimensiones del tablero
	public static final int DIM_Y = 15;
	
	private int points;
	private int remainingTime;
	private int lives;
	private Mario mario;
	private boolean win;//true si ha mario ha ganado
	private boolean exitComando;//true si se le ha dado al comando de exit
	private GameObjectContainer gameObjects=new GameObjectContainer();//Objecto de GameObjectContainer donde se guardan todos los elementos del juego.
	private GameConfiguration fileLoader;
	boolean reset;
	
	public Game(int nLevel) throws GameLoadException{//Constructor de Game
		try{
			this.load("initLevel2");
		}catch(GameLoadException e) {
			throw e;
		}
		this.points=0;
		this.lives=3;
		this.remainingTime=100;
		this.exitComando=false;
		this.win=false;
		this.reset=false;
		reset(nLevel);//se inicia el nivel que uno quiero, se puede cambiar al 1 
	}
	
	//Métodos de GameModel
	
	@Override
	public void save(String fileName)throws GameModelException{
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter((fileName+".txt")));//se abre el archivo en el que vamos a escribir
			List<String> save=new ArrayList<String>();
			bw.write(Integer.toString(remainingTime));//se escribe el game status
			bw.write(" ");
			bw.write(Integer.toString(points));
			bw.write(" ");
			bw.write(Integer.toString(lives));
			bw.newLine();
			save=gameObjects.saveObjects(save);//se guarda la lista de objetos
			for(int i=0; i<save.size();++i) {
				bw.write(save.get(i));//se van escribiendo los objetos uno a uno
			}
			bw.close();//se cierra el buffer SIEMPRE
		}
		catch(IOException e){//en caso de que no se haya salvado el archivo se manda este error
			throw new GameModelException(String.format(Messages.SAVE_ERROR, fileName));
		}
	}
	
	@Override
	public void load(String fileName)throws GameLoadException{
		try{
			GameConfiguration fgc=new FileGameConfiguration(fileName, this);//se manda crear la nueva configuracion del juego
			fileLoader=fgc;//se guarda la configuracion, porque si ha llegado hasta aqui es porque se ha guardado con exito, y hay que guardarla para poder resetearla en caso de querer
			reset=false;
			reset();//se hace reset al nuevo fileLoader
			reset=true;
		}catch(GameLoadException e) {//si hay cualquier fallo en la carga, se ha lanzado esta excepcion
			throw new GameLoadException(Messages.UNABLE_LOAD.formatted(fileName), e);
		}
	}
	
	@Override
	public void update() {//actualiza el juego y resta 1 segundo al tiempo por cada actualizaci�n
		--this.remainingTime;
		gameObjects.update();
	}
	@Override
	public void reset(int file)throws GameLoadException{//reset cuando se manda un nivel como argumento
		try{
            switch (file) {
                case 0 -> this.load("initLevel0");
                case 1 -> this.load("initLevel1");
                case -1 -> this.load("initLevel_1");
                default -> this.load("initLevel2");
            }
		}
		catch(GameLoadException e) {//no da excepcion porque las configuraciones son correctas, pero hay que ponerlo en caso de que no esten donde tienen que estar, o se cambie algo de ellas
			throw e;
		}
	}
	@Override
	public void reset()throws GameLoadException{//reset sin argumento(se reinicia en el nivel en el que est�s)
		//se clona la lista de objetos para que no apunten al mismo objeto en memoria
		List<GameObject> objetosClonados=fileLoader.getNPCObjects();
		GameObjectContainer goc=new GameObjectContainer();
		goc.clonarObject(objetosClonados);
		this.gameObjects=goc;
		this.mario=fileLoader.getMario();
		goc.add(mario);
		if(!reset) {
			this.lives=fileLoader.numLives();
			this.points=fileLoader.points();			
		}
		this.remainingTime=fileLoader.getRemainingTime();

	}
	@Override
	public void exit() {//se le ha dado al comando de exit
		exitComando=true;
	}
	@Override
	public boolean addObject(String[] objWords2)throws OffBoardException, GameParseException{ //se utiliza para el addObjectCommand, que dado todo el array de palabras, gameobjectfactory devuelve el objecto correcto
		String[] objWords=new String[objWords2.length-1];
		for(int i=0; i<objWords2.length-1;++i){
			objWords[i]=objWords2[i+1];
		}
		GameObject obj=GameObjectFactory.parse(objWords, this);
		boolean n=false;
		if(obj!=null) { //si no es nulo se añade a la lista de gameobjects
			gameObjects.add(obj);
			n=true;
		}
		return n;//devuelve true si el objeto no es nulo
	}
	@Override
	public boolean isFinished() {//devuelve true si el juego ha terminado para as� terminar el bucle de controller
		return win||this.playerLoses()||exitComando;
	}
	@Override
	public void addAction(Action action) {//a�ade una acci�n a la lista
		mario.addAction(action);
	}
	
	//Métodos de GameWorld
	@Override
	public void borrarObject(GameObject obj) {//se borra el objeto de la lista
		gameObjects.borrarObject(obj);
	}
	@Override
	public void marioExited() {//se llama si mario ha tocado la puerta, y se suman los puntos correspondientes.
		this.points=this.points+this.remainingTime*10;
		win=true;
	}
	@Override
	public void doInteractionsFrom(GameObject object) {
		gameObjects.doInteractionsFrom(object);
	}
	
	@Override
	public void puntosGoombaMuerto() {//se suman los puntos por haber matado a un goomba.
		this.points+=100;
	}
	@Override
	public void puntosBox() { //se suma los puntos por haberle dado a una caja
		this.points+=50;
	}
	@Override
	public void addObject(GameObject obj) { //se añade el objeto correspondiente(ahora mismo solo vale para añadir el champiñon después de darle a la caja
		gameObjects.add(obj);
	}
	@Override
	public void actualizarMario(Mario mario2) { //si se añade un mario con addObject, el que se controla es el nuevo
		this.mario=mario2;
	}

	@Override
	public void restarVida() {//se resta una vida
		this.lives--;
	}
	@Override
	public boolean isSolid(Position pos) {//true si hay solido en esa posicion
		return gameObjects.isSolid(pos);
	}
	@Override
	public void marioHaMuerto() throws GameLoadException{ //resta vida y resetea el juego
		restarVida();
		try{
			reset=true;
			reset();
		}catch(GameLoadException e) {//no deberia dar fallo, pero para reutilizar el metodo hay que hacerlo
			throw e;
		}
	}


	//Métodos de GameStatus
	/*
	@Override
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
		int tamX = 8;
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
	
	@Override
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
	@Override
	public void initLevel2() {//inicia el nivel 2
		this.initLevel1();
		this.nLevel=2;
		
		gameObjects.add(new Box(this, new Position(9,4)));
		gameObjects.add(new Mushroom(this, new Position(12,8)));
		gameObjects.add(new Mushroom(this, new Position(2,20)));


		
	}
	@Override
	public void initLevel_1() { //inicia el nivel -1, que es el tablero vacio
		gameObjects = new GameObjectContainer();

		this.nLevel = -1;
		this.remainingTime = 100;
		this.points=0;
		this.lives=3;
		
	}
	*/
	@Override
	public String positionToString(int col, int row) {//esta funci�n esta dentro de un bucle que genera todo el tablero
		Position pos=new Position(row, col);	
		return gameObjects.positionToString(pos);
	}
	@Override
	public int remainingTime() {//devuelve el tiempo que queda
		return this.remainingTime;
	}
	@Override
	public int points() {//devuelve los puntos
		return this.points;
	}
	@Override
	public int numLives() {//devuelve las vidas
		return this.lives;
	}
	@Override
	public boolean playerLoses() {//true si se han acabado las vidas o el tiempo
		return this.lives==0||this.remainingTime==0;
	}
	@Override
	public boolean playerWins() {//devuelve true si ha ganado
		return win;
	}
		
	@Override
	public String toString() {
		// TODO returns a textual representation of the object
		return "TODO: Hola soy el game";
	}
}
