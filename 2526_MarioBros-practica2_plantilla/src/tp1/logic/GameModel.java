package tp1.logic;
import tp1.exceptions.*;

public interface GameModel {//funciones del controlador(comandos)
	public void update();
	public void reset() throws GameLoadException;
	public void reset(int n) throws GameLoadException;
	public void exit();
	public void save(String fileName) throws GameModelException;
	public void load(String fileName) throws GameLoadException;
	public boolean isFinished();
	public void addAction(Action action);
	public boolean addObject(String[] objWords)throws OffBoardException, GameParseException;
}
