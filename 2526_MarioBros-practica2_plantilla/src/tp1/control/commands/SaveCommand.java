package tp1.control.commands;

import tp1.exceptions.*;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class SaveCommand extends AbstractCommand{
	private static final String NAME = Messages.COMMAND_SAVE_NAME;
    private static final String SHORTCUT = Messages.COMMAND_SAVE_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_SAVE_DETAILS;
    private static final String HELP = Messages.COMMAND_SAVE_HELP;
    private String fileName;
    
    public SaveCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
    }
    
    public SaveCommand(String s) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		fileName=s;
		
    }
 
	 public Command parse(String[] commandWords) throws CommandParseException{
		Command c=null;
		if(matchCommandName(commandWords[0])&&commandWords.length==2) {//si coincide con el comando y son dos palabras entonces se devuelve este comando
			c=new SaveCommand(commandWords[1]);
		}
		else if(matchCommandName(commandWords[0])&&commandWords.length>=1)throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);//si no hay nada despues de save da fallo
		return c;
		}
	   
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
		try {
		game.save(fileName);//se llama al metodo de game para guardar el juego en el archivo que se haya dicho
		view.showMessage(String.format(Messages.SAVE_CORRECT, fileName));//se muestra este mensaje si se ha salvado correctamente el archivo
		}
		catch(GameModelException e) {//no deberia dar fallo, pero en caso de haberlo se llamaria a este(en ese caso seria un fallo para abrir y escribir en el archivo)
			throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
		}
	}
}
