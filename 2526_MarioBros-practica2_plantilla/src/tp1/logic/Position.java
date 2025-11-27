package tp1.logic;
import tp1.exceptions.*;
import tp1.view.Messages;
/**
 * 
 * TODO: Immutable class to encapsulate and manipulate positions in the game board
 * 
 */
public class Position {
	private int col;
	private int row;
	
	public Position(int row, int col) {//el constructor de Position
		this.col=col;
		this.row=row;
	}
	
	public boolean comparePos(Position pos2) {//devuelve true si dos posiciones son iguales
		return this.col==pos2.col&&this.row==pos2.row;
		
	}
	public Position devolverAbajo() {//devuelve la posicion de abajo
		return new Position(this.row+1, this.col);
		
	}
	public Position devolverIzquierda() {//devuelve la posicion de la izquierda
		return new Position(this.row, this.col-1);
		
	}
	public Position devolverDerecha() {//devuelve la posicion de la derecha
		return new Position(this.row, this.col+1);
	}
	public Position devolverArriba() {//devuelve la posicion de arriba
		return new Position(this.row-1, this.col);
	}
	
	public boolean outOfBounds() {//comprueba que la posicion esta dentro del tablero
	    return row < 0 || row >= Game.DIM_Y || col < 0 || col >= Game.DIM_X;
	}
	
	public static Position leerPosition(String[] objWords)throws PositionParseException, OffBoardException{
		int a, b;
		//con esto se guarda los enteros que representan la posicion
		String posicionn= objWords[1].replace("(", "").replace(")", "");
		String[] posicion = posicionn.split(",");
		try {
		a = Integer.parseInt(posicion[0].trim());
		}
		catch (NumberFormatException e) {
		    throw new PositionParseException(Messages.INVALID_POSITION.formatted(objWords[1]), new ArrayIndexOutOfBoundsException(Messages.INPUT_STRING.formatted(posicion[0])));
		}
		catch (ArrayIndexOutOfBoundsException e) {
		    throw new PositionParseException(Messages.INVALID_POSITION.formatted(objWords[1]));
		}
		try {
			b = Integer.parseInt(posicion[1].trim());
		}
		catch (ArrayIndexOutOfBoundsException e) {
		    throw new PositionParseException(Messages.INVALID_POSITION.formatted(objWords[1]));
		}
		catch (NumberFormatException e) {
		    throw new PositionParseException(Messages.INVALID_POSITION.formatted(objWords[1]), new ArrayIndexOutOfBoundsException(Messages.INPUT_STRING.formatted(posicion[1])));
		}
		Position pos= new Position(a,b);
		if(pos.outOfBounds())throw new OffBoardException();
		return pos;
	}
}
