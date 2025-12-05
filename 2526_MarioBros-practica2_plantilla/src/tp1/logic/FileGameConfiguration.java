package tp1.logic;

import tp1.exceptions.*;
import tp1.view.Messages;
import tp1.logic.gameobjects.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileGameConfiguration implements GameConfiguration{
	private String gameStatus;
	private int remainingTime;
	private int numLives;
	private int points;
	private Mario mario;
	private List<GameObject> obj=new ArrayList<GameObject>();
	public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException{
		try {
			BufferedReader bw=new BufferedReader(new FileReader((fileName+".txt")));//se abre el archivo con el nombre que se haya mandado(se supone que es txt asi que se le anade el sufijo)
			String linea;
			String[] objWords;
			linea=bw.readLine();//se lee la primera linea, que es el game status(o deberia serlo)
			gameStatus=linea;
			objWords=linea.split(" ");
			if(objWords.length!=3) {
				bw.close();
				throw new ArrayIndexOutOfBoundsException();//si no hay 3 palabras da fallo porque siempre tiene que dar 3 cosas, tiempo, vidas, puntos
			}
			remainingTime=Integer.parseInt(objWords[0]);//se intenta parsear el game status
			points=Integer.parseInt(objWords[1]);
			numLives=Integer.parseInt(objWords[2]);
			while((linea=bw.readLine())!=null) {//se va leyendo hasta que la linea sea nula
				objWords=linea.split(" ");//se separan las palabras en un array
				GameObject parse=GameObjectFactory.parse(objWords, game);
				//se leen todos los objetos, y si en tiempo de compilacion la clase del objeto es mario, se guarda como atributo, en cualquier caso se anade a la lista de objetos
				if(parse==null) {//si da fallo al leer el objeto se lanza esta excepcion(solo si el objeto es desconocido)
					bw.close();
					throw new GameLoadException(Messages.UNKNOWN_GAME_OBJECT.formatted(String.join(" ", objWords)));
				}
				else if(objWords[1].equalsIgnoreCase(Messages.MARIO_NAME)||objWords[1].equalsIgnoreCase(Messages.MARIO_SHORTCUT)) {
					mario=(Mario)parse;
					
				}
				else obj.add(parse);
			}
			bw.close();//se cierra el buffer SIEMPRE
		}catch(IOException e) {//si no se encuentra el archivo o algo similar se manda eseta excepcion
			throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName), new IOException(Messages.NAME_FILE.formatted(fileName)));
		}
		catch(GameModelException e) {//cualquier fallo al leer los objetos, se engloban todas(offBoard, objectParse y PositionParse
			throw new GameLoadException(Messages.INVALID_CONFIGURATION.formatted(fileName), e);
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {//esta excepcion se manda si el gamestatus es incorrecto
			throw new GameLoadException(Messages.INVALID_GAME_STATUS.formatted(gameStatus));
		}
	}
	@Override
	public int getRemainingTime() {//dvuelve el tiempo leido
		return remainingTime;
	}
	
	@Override
	public int numLives() {//devuelve el numero de vidas leido
		return numLives;
	}
	
	@Override
	public int points() {//devuelve los puntos leidos
		return points;
	}
	
	@Override
	public Mario getMario() {//devuelve al mario leido
		Mario mario2=new Mario();
		mario2=(Mario)mario.clonarObject();
		return mario2;
	}
	
	@Override
	public List<GameObject> getNPCObjects() {//devuelve una lista clonada de los objetos leidos
		List<GameObject> clonar=new ArrayList<>();
		for(GameObject gameObject:obj) {
			clonar.add(gameObject.clonarObject());
		}
		return clonar;
	}
}
