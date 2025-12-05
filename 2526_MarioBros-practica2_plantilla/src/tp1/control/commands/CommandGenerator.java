package tp1.control.commands;

import java.util.Arrays;
import java.util.List;
import tp1.exceptions.*;
import tp1.view.Messages;

public class CommandGenerator {

	private static final List<Command> availableCommands = Arrays.asList(
			new LoadCommand(),
			new SaveCommand(),
			new AddObjectCommand(),
			new ActionCommand(),
	        new UpdateCommand(),
	        new ResetCommand(),
	        new HelpCommand(),
	        new ExitCommand());
			
	public static Command parse(String[] commandWords) throws CommandParseException{ //recorre todos los comandos, y si coincide con alguno devuelve ese, en caso contrario devuelve null
		Command result=null;
		try {
		for (Command c: availableCommands) {
			if(result==null)result=c.parse(commandWords);
		}
		if(result==null&&"".equalsIgnoreCase(commandWords[0]))result=availableCommands.get(4);//si no hay nada en el array se hace update
		else if(result==null)throw new CommandParseException(Messages.UNKNOWN_COMMAND.formatted(String.join(" ", commandWords)));
		} catch(CommandParseException exception){
			throw exception;
		}
		return result;
	}
		
	public static String commandHelp() { //recoge todos los helps de todos los comandos, y devuelve el texto completo con todos
		StringBuilder commands = new StringBuilder();
		
		commands.append(Messages.HELP_AVAILABLE_COMMANDS).append(Messages.LINE_SEPARATOR);
		
		for (Command c: availableCommands) {
			commands.append(c.helpText());
		}
		return commands.toString();
	}
}
