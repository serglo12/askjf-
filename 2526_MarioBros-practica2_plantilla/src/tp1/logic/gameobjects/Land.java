package tp1.logic.gameobjects;
import tp1.view.Messages;

import java.util.Arrays;

import tp1.exceptions.*;
import tp1.logic.*;

public class Land extends GameObject{	
	private static final String NAME=Messages.LAND_NAME;
	private static final String SHORTCUT=Messages.LAND_SHORTCUT;
	
	public Land(GameWorld game, Position pos) {//constructor de la tierra
		super(game, pos);
	}
	
	public Land() {//constructor vacio de land, se llama al constructor por defecto de gameobject(esta creado)
		cambiarNombres(NAME,SHORTCUT);
	}
	@Override
	public String getIcon() {//devuelve el icono de tierra
		return Messages.LAND;
	}
	@Override
	public GameObject parse (String objWords[], GameWorld game, Position pos) throws ObjectParseException{//devuelve el nuevo land en caso de ser el formato correcto, en otro caso devuelve null
		GameObject c=null;
		if(matchCommandName(objWords[2])&&objWords.length==3) {
			c=new Land(game, pos);
		}
		else if(matchCommandName(objWords[2])) throw new ObjectParseException(Messages.PARSE_INCORRECT_PARAMETER_NUMBER.formatted( String.join(" ", Arrays.copyOfRange(objWords, 1, objWords.length))));
		return c;
	}
}