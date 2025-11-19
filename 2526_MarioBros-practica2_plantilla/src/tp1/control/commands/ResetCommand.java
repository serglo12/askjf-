package tp1.control.commands;

import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends AbstractCommand{
	
	private static final String NAME = Messages.COMMAND_RESET_NAME;
    private static final String SHORTCUT =  Messages.COMMAND_RESET_SHORTCUT;
    private static final String DETAILS =  Messages.COMMAND_RESET_DETAILS;
    private static final String HELP =  Messages.COMMAND_RESET_HELP;
    
    private int nLevel;//para guardar el numero del nivel, es -1 si no hay ninguno
    private String linea;//lo necesitamos para poder mostrar lo que ha puesto el usuario
    
	public ResetCommand() { //constructor de reset, inicia nLevel a un nivel no existente
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.nLevel=-2;
		this.linea="";
	}
	@Override
	public Command parse(String[] commandWords) {//parse de reset, devuelve reset si coincide la primera palabra del array con el nombre o shortcut, y también guarda el número al que se resetea en caso de haberlo
		Command c=null;
		if(matchCommandName(commandWords[0])) {
			if (commandWords.length == 2) {//true si hay algo despues de reset
				 String valor = commandWords[1];
				 if (valor.matches("-?\\d+")) {//si es un numero
					 nLevel = Integer.parseInt(valor);
				 }
				 else linea = String.join(" ", commandWords);//si hay más palabras además del número da fallo, y se guarda la línea para mostrarla al usuario
			}
			c=this;
		}
		return c;
	}
	@Override
	public void execute(GameModel game, GameView view) {//ejecuta reset
		if(nLevel>=-1&&nLevel<=2) {//se tienen en cuenta todos los enteros de [-1,2]
		    game.reset(nLevel);
		    view.showGame();
		}
		else {//el resto de n�meros son inv�lidos
			if(!"".equals(linea)) {//la segunda palabra despu�s de reset no es un n�mero
		    	view.showMessage(String.format(Messages.LEVEL_NOT_A_NUMBER_ERROR,linea));
		    }
			else if(nLevel!=-2){ //número incorrecto
				view.showError(String.format(Messages.INVALID_LEVEL_NUMBER, nLevel));
			}	
			else { // si no hay nada se resetea al nivel en el que estuviese
				game.reset();
				view.showGame();
			}
		}
		//reiniciamos los valores de nLevel y linea para la pr�xima vez que se use reset
		this.nLevel=-2;
		this.linea="";
	}	
}