package tp1.logic;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;

public interface GameWorld {//funciones sobre el funcionamente del juego
	public String positionToString(Position pos);
	public void marioExited();
	public void borrarObject(GameObject obj);
	public void doInteractionsFrom(GameObject object);
	public void puntosGoombaMuerto();
	public void puntosBox();
	public void addObject(GameObject obj);
	public void actualizarMario(Mario mario2);
	public void restarVida();
	public boolean isSolid(Position pos);
	public void marioHaMuerto();
}
