package tp1.control.commands;

public abstract class NoParamsCommand extends AbstractCommand {

	public NoParamsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}

	@Override
	public Command parse(String[] commandWords) {//devuelve el comando correspondiente(help o exit, el que sea) si solo tiene una palabra el array y coincide con dicho comando
		Command c=null;
		if(commandWords.length<=1) {
			if(matchCommandName(commandWords[0])) {
				c=this;
			}
		}
		return c;
	}
}
