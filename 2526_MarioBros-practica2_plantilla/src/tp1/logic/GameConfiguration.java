package tp1.logic;
import tp1.logic.gameobjects.*;
import java.util.List;

public interface GameConfiguration {
	public int getRemainingTime();
	public int points();
	public int numLives();
	public Mario getMario();
	public List<GameObject> getNPCObjects();
}
