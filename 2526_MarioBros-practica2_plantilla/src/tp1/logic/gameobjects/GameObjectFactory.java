package tp1.logic.gameobjects;
import java.util.Arrays;
import java.util.List;
import tp1.logic.Position;

import tp1.logic.GameWorld;

public class GameObjectFactory {
	
	public GameObjectFactory() {
	}
	
	private static final List<GameObject> availableObjects = Arrays.asList( //todos los posibles objetos
			new Land(),
			new ExitDoor(),
			new Goomba(),
			new Mario(), 
			new Mushroom(),
			new Box());

	public static GameObject parse (String[] objWords, GameWorld game) {//devuelve el objeto correspondiente si el formato es correcto, si no lo es o si no existe dicho objeto, devuelve null
		GameObject result=null;
		//con esto se guarda los enteros que representan la posicion
		objWords[1] = objWords[1].replace("(", "").replace(")", "");
        String[] posicion = objWords[1].split(",");

        int a = Integer.parseInt(posicion[0].trim());
        int b = Integer.parseInt(posicion[1].trim());
        Position pos= new Position(a,b);//se crea la nueva posicion
        if(!pos.outOfBounds()) {//si la posicion es valida
        	for (GameObject c: availableObjects) {
				if(result==null)result=c.parse(objWords, game, pos);//llama al parse especifico de cada objeto
			}
        }
		return result;//devuelve el resultado
	}
}
