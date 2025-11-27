package tp1.control.commands;

import tp1.view.Messages;

public abstract class AbstractCommand implements Command{

	// Forman parte de atributos de estado
	private final String name;
	private final String shortcut;
	private final String details;
	private final String help;
	
	public AbstractCommand(String name, String shorcut, String details, String help) { //constructor de comandos
		this.name = name;
		this.shortcut = shorcut;
		this.details = details;
		this.help = help;
	}
	
	//getters de los atributos

	protected String getName() { return name; }
	protected String getShortcut() { return shortcut; }
	protected String getDetails() { return details; }
	protected String getHelp() { return help; }

	protected boolean matchCommandName(String name) {//compara si el atributo name coincide con el comando
		return getShortcut().equalsIgnoreCase(name) || 
			   getName().equalsIgnoreCase(name);
	}

	@Override
	public String helpText(){//devuelve el atributo de help del comando y al hacer el comando help, se llama al de todos los tipos de comandos y al unirlos sale el texto completo de ayuda
		return Messages.LINE_TAB.formatted(Messages.COMMAND_HELP_TEXT.formatted(getDetails(), getHelp()));
	}
}