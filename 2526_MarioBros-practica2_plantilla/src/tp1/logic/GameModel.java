package tp1.logic;

public interface GameModel {//funciones del controlador(comandos)
	public void update();
	public void reset();
	public void reset(int n);
	public void exit();
	public boolean isFinished();
	public void addAction(Action action);
	public boolean addObject(String[] objWords);
	public boolean marioHaPerdido();
}
