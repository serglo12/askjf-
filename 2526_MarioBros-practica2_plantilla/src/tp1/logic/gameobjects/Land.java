package tp1.logic.gameobjects;
import tp1.view.Messages;
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
	public GameObject parse (String objWords[], GameWorld game, Position pos) {//devuelve el nuevo land en caso de ser el formato correcto, en otro caso devuelve null
		GameObject c=null;
		if(matchCommandName(objWords[2])&&objWords.length==3) {
			c=new Land(game, pos);
		}
		return c;
	}
}