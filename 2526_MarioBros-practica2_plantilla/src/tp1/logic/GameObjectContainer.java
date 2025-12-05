package tp1.logic;

import java.util.ArrayList;
import java.util.List;
import tp1.logic.gameobjects.GameObject;

public class GameObjectContainer{
	private List<GameObject> objects;//lista de objetos del juego
	

	public GameObjectContainer() {//contructor de GameObjectContainer
		objects = new ArrayList<GameObject>();
	}
	
	public void add(GameObject object) {//añade un objeto a la lista
		objects.add(object);
	}
	
	public void borrarObject(GameObject object) {//borra un objecto de la lista
		objects.remove(object);
	}
	
	public void clonarObject(List<GameObject> nuevosObjects) {
		this.objects=new ArrayList<>(nuevosObjects);
	}
	
	public List<String> saveObjects(List<String> obj){//se guardan todos los objetos en una lista de strings
		for(int i=0; i<objects.size();++i) {
			objects.get(i).saveObject(obj);
		}
		return obj;
	}
	
	public String positionToString(Position pos) {//devuelve el icono del objeto que haya en esa posicion
		boolean encontrado=false;
		String a="";
		for(int i=0; i<objects.size()&&!encontrado;++i) {
			if(objects.get(i).isInPosition(pos)) {
				encontrado=true;
				a=objects.get(i).getIcon();
			}
		}
		return a;
	}
	
	public boolean isSolid(Position pos) {//recorre toda lista para comprobar si en la posicion que se ha mandado como argumento hay solido o no
		boolean solido=false, encontrado=false;
		for(int i=0; i<objects.size()&&!encontrado;++i) {
			if(objects.get(i).isInPosition(pos)) {
				solido=objects.get(i).isSolid();
				encontrado=true;
			}
		}
		return solido;		
	}
	
	public void doInteractionsFrom(GameObject object) {//recorre toda la lista de objetos haciendo todas las interacciones por ambos partes, por ejemplo, si son mario y goomba, se hacen las de mario a goomba y las de goomba a mario
		for(int i=0; i<objects.size();++i) {
			if(object.isAlive()) {
				GameObject object2=objects.get(i);
				object2.interactWith(object);
				object.interactWith(object2);
			}
		}
	}
	
	public void update() {//recorre toda la lista de objetos, hace update del objeto que toque y despues las interacciones del objeto que acaba de hacer update
		for(int i=0; i<objects.size();++i) {
			GameObject object=objects.get(i);
			if(!object.isSolid()&&object.isAlive()) {
				object.update();
				this.doInteractionsFrom(object);
			}
		}
	}
}