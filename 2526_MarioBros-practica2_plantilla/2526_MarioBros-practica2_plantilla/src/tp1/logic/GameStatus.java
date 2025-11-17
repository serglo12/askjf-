package tp1.logic;

public interface GameStatus {//funciones sobre la interfaz
	public String positionToString(int col, int row);	
	public void initLevel0();
	public void initLevel1();
	public void initLevel_1();
	public void initLevel2();
	public int remainingTime();
	public int points();
	public int numLives();
	public boolean playerLoses();
	public boolean playerWins();
}
