package tp1.logic;
import tp1.exceptions.*;

public interface GameModel {//funciones del controlador(comandos)
	public void update();
	public void reset();
	public void reset(int n);
	public void exit();
	public boolean isFinished();
	public void addAction(Action action);
	public boolean addObject(String[] objWords)throws OffBoardException, ObjectParseException, PositionParseException;
	public boolean marioHaPerdido();
}
