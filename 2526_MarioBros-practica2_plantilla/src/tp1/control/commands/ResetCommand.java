package tp1.control.commands;

import tp1.logic.GameModel;
import tp1.exceptions.*;
import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends AbstractCommand{
	
	private static final String NAME = Messages.COMMAND_RESET_NAME;
    private static final String SHORTCUT =  Messages.COMMAND_RESET_SHORTCUT;
    private static final String DETAILS =  Messages.COMMAND_RESET_DETAILS;
    private static final String HELP =  Messages.COMMAND_RESET_HELP;
    
    private int nLevel;//para guardar el numero del nivel, es -1 si no hay ninguno
    
	public ResetCommand() { //constructor de reset, inicia nLevel a un nivel no existente
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	public ResetCommand(int nLevel2) { //constructor de reset, inicia nLevel a un nivel no existente
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.nLevel=nLevel2;
	}
	
	@Override
	public Command parse(String[] commandWords) throws CommandParseException{//parse de reset, devuelve reset si coincide la primera palabra del array con el nombre o shortcut, y también guarda el número al que se resetea en caso de haberlo
		Command c=null;
		int nLevel2=Integer.MIN_VALUE;
		try {
			if(matchCommandName(commandWords[0])) {//si se ha puesto reset
				if (commandWords.length == 2) {//true si hay algo despues de reset
					String valor = commandWords[1];
					if (valor.matches("-?\\d+")) {//si es un numero
						nLevel2 = Integer.parseInt(valor);
						c=new ResetCommand(nLevel2);
					}
					else {
						throw new NumberFormatException(String.format(Messages.INPUT_STRING, commandWords[1]));//se lanza la excepcion con el input que ha dado el fallo
						}
				}
				else if(commandWords.length==1)c=new ResetCommand(nLevel2);
				else throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			}
		}catch(NumberFormatException nfe) {//si no parsea el numero se lanza esta excepcion
			throw new CommandParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(commandWords[1]), nfe);	
		}
		return c;
	}
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{//ejecuta reset
		try{
			if(nLevel>=-1&&nLevel<=2) {//se tienen en cuenta todos los enteros de [-1,2]
		    game.reset(nLevel);
		    view.showGame();
		}
		else {//el resto de n�meros son inv�lidos
			if(nLevel!=Integer.MIN_VALUE){ //número incorrecto
				throw new CommandExecuteException(String.format(Messages.INVALID_LEVEL_NUMBER, nLevel));
			}	
			else { // si no hay nada se resetea al nivel en el que estuviese
				game.reset();
				view.showGame();
			}
		}
		}catch(GameLoadException e) {//no va a saltar ningun error porque las configuraciones estan bien hechas, pero hay que ponerlo porque al usar el metodo de load es necesario
			throw new CommandExecuteException(Messages.UNABLE_LOAD.formatted(nLevel), e);
		}
	}	
}