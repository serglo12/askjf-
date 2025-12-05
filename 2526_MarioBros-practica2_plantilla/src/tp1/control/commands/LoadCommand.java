package tp1.control.commands;
import tp1.exceptions.*;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class LoadCommand extends AbstractCommand {
	private static final String NAME = Messages.COMMAND_LOAD_NAME;
    private static final String SHORTCUT = Messages.COMMAND_LOAD_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_LOAD_DETAILS;
    private static final String HELP = Messages.COMMAND_LOAD_HELP;
    private String fileName;
    
    public LoadCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
    }
    
    public LoadCommand(String s) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		fileName=s;
		
    }
 
    @Override
	public Command parse(String[] commandWords) throws CommandParseException{
		Command c=null;
		if(matchCommandName(commandWords[0])&&commandWords.length==2) {//si tiene dos palabras y la primera coincide con el comando se crea el comando load
			c=new LoadCommand(commandWords[1]);
		}
		else if(matchCommandName(commandWords[0]))throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);//si no hay nada despues de load da fallo
		return c;
		}
	 
    @Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
		try {
			game.load(fileName);//se carga el juego que haya en el nombre del archivo correspondiente 
			view.showGame();
		}catch(GameLoadException e) {//cualquier tipo de excepcion al cargar el juego, como mal formato o archivo no encontrado, entre otros.
			throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
		}
	}
}
