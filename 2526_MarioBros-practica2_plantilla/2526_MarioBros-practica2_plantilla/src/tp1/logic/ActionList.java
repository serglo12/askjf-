package tp1.logic;
import java.util.List;
//import tp1.logic.gameobjects.*;
import java.util.ArrayList;


public class ActionList {
	private List<Action> actionList;//lista de todas las acciones, correctas e incorrectas
	
	public ActionList() {//Constructor de ActionList
		actionList=new ArrayList<>();
	}
	
	public void addAction(Action action) {//a�ade una acci�n a la lista de acciones
		actionList.add(action);
	}


	public List<Action> listaDeAcciones(){/*devuelve la lista correcta, en la que no hay m�s de 4 up/downs(no pueden estar ambos en la lista correcta), ni m�s de 4 right/left(no pueden estar ambos en la lista correcta).
		Lo hace guardando un contador y un action por cada direcci�n, para llevar tanto la cuenta como cual de los dos sentidos es el correcto(el primero que aparezca) */
	    List<Action> actionListToDo=new ArrayList<>();
	    Action horizontal=null;//las iniciamos a null
	    Action vertical=null; 
	    int contadorHorizontal=0, contadorVertical=0;

	    for (int i = 0; i < actionList.size(); i++) {//va comprobando por cada acción si se añade a la lista o no, según la dirección y el contador
	        Action actionActual=actionList.get(i);
	        
	        if (actionActual==Action.LEFT||actionActual==Action.RIGHT) {
	            if (horizontal==null) horizontal = actionActual;      // fija la primera direcci�n horizontal
	            if (actionActual==horizontal) {
	                if (contadorHorizontal < 4) { //si hay más de 4 no se añade
	                    actionListToDo.add(actionActual);
	                    contadorHorizontal++;
	                }
	            } 
	        }
	        else if (actionActual==Action.UP||actionActual==Action.DOWN) {
	            if (vertical==null)vertical=actionActual;      // fija la primera direcci�n vertical
	            if (actionActual==vertical) {
	                if (contadorVertical < 4) {//si hay más de 4 no se añade
	                    actionListToDo.add(actionActual);
	                    contadorVertical++;
	                }
	            }
	            
	        }
	        else actionListToDo.add(actionActual); //si es stop se añade en cualquier caso 
	    }
	    actionList.clear();
	    return actionListToDo;
	}
}
