package tp1.control.commands;
import tp1.exceptions.*;
import tp1.view.Messages;

public abstract class NoParamsCommand extends AbstractCommand {

	public NoParamsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}

	public Command parse(String[] commandWords)throws CommandParseException{//devuelve el comando correspondiente(help o exit, el que sea) si solo tiene una palabra el array y coincide con dicho comando
		if (commandWords.length > 1 && matchCommandName(commandWords[0]))throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);//solo da fallo si la primera palabra es correcta y hay argumentos de mas ,en otro caso se devuelve null
		Command c=null;
		if(commandWords.length==1&&matchCommandName(commandWords[0])) {//si hay una sola palabra y ademas coincide con el comando, se envia el respectivo comando en el que nos encontremos
			if(matchCommandName(commandWords[0])) {
				c=this;
			}
		}
		return c;
	}
}
