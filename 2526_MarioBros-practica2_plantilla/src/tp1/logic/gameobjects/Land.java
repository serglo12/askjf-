package tp1.logic.gameobjects;
import tp1.view.Messages;

import java.util.List;

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
		if(matchCommandName(objWords[1])&&objWords.length==2) {//si coincide con land y solo hay dos palabras se crea dicho objeto
			c=new Land(game, pos);
		}
		else if(matchCommandName(objWords[1])) throw new ObjectParseException(Messages.PARSE_INCORRECT_PARAMETER_NUMBER.formatted( String.join(" ", objWords)));//si hay mas palabras de las necesarias, se manda esta excepcion
		return c;
	}
	
	protected void leerEspecifico(List<String> lista) {//se lee lo especifico del objeto
		lista.add(NAME);
	}
	@Override
	protected GameObject clonarEspecifico() {
		Land e=new Land();
		return e;
	}
}