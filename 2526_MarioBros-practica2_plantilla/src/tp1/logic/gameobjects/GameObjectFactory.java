package tp1.logic.gameobjects;
import java.util.Arrays;
import java.util.List;
import tp1.exceptions.*;
import tp1.view.Messages;
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

	public static GameObject parse (String[] objWords, GameWorld game)throws OffBoardException, ObjectParseException, PositionParseException{//devuelve el objeto correspondiente si el formato es correcto, si no lo es o si no existe dicho objeto, devuelve null		
		try {
			GameObject result=null;
			Position pos= Position.leerPosition(objWords);//se crea la nueva posicion
			for (GameObject c: availableObjects) {
				if(result==null)result=c.parse(objWords, game, pos);//llama al parse especifico de cada objeto
			}	
			return result;//devuelve el resultado
		}catch(OffBoardException e) {
			throw new OffBoardException(Messages.POSITION_OUT_BOUNDS.formatted( String.join(" ", Arrays.copyOfRange(objWords, 1, objWords.length))));
		}
		catch( PositionParseException e) {
			throw new ObjectParseException(Messages.INVALID_OBJECT_POSITION.formatted( String.join(" ", Arrays.copyOfRange(objWords, 1, objWords.length))), e);
		}
		catch(ObjectParseException e) {
			throw e;
		}	
		catch( ActionParseException e) {
			if(e.getMessage()!=null) throw new ObjectParseException(Messages.UNKNOWN_DIRECTION.formatted( String.join(" ", Arrays.copyOfRange(objWords, 1, objWords.length))), e);
			else throw new ObjectParseException(Messages.INVALID_DIRECTION.formatted(String.join(" ", Arrays.copyOfRange(objWords, 1, objWords.length))));
		}
	}
}
