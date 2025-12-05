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

	public static GameObject parse (String[] objWords, GameWorld game)throws OffBoardException, GameParseException{//devuelve el objeto correspondiente si el formato es correcto, si no lo es o si no existe dicho objeto, devuelve null		
		try {//se intenta leer la posicion, y parsear el objeto
			GameObject result=null;
			Position pos= Position.leerPosition(objWords);//se crea la nueva posicion
			for (GameObject c: availableObjects) {
				if(result==null)result=c.parse(objWords, game, pos);//llama al parse especifico de cada objeto
			}
			return result;//devuelve el resultado, null si el objeto es desconocido
		}catch(OffBoardException e) {//si la posicion tiene el formato correcto pero esta fuera de tablero
			throw new OffBoardException(Messages.POSITION_OUT_BOUNDS.formatted( String.join(" ", objWords)));
		}
		catch( PositionParseException e) {//si la posicion tiene formato incorrecto
			throw new GameParseException(Messages.INVALID_OBJECT_POSITION.formatted( String.join(" ", objWords)), e);
		}
		catch(ObjectParseException e) {//cualquier tipo de fallo en el formato del objeto
			throw e;
		}	
		catch( ActionParseException e) {//si el objeto tiene direccion, si la excepcion no tiene mensaje es poruqe es desconocida, en otro caso es invalida
			if(e.getMessage()!=null) throw new GameParseException(Messages.UNKNOWN_DIRECTION.formatted( String.join(" ",objWords)), e);
			else throw new GameParseException(Messages.INVALID_DIRECTION.formatted(String.join(" ", objWords)));
		}
	}
}
