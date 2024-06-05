package Ascii4UwUWareClient.Manager;

import Ascii4UwUWareClient.API.EventBus;
import Ascii4UwUWareClient.Command.Command;
import Ascii4UwUWareClient.Command.Commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager implements Manager {
	private static List<Command> commands;

	@Override
	public void init() {
		this.commands = new ArrayList<Command>();
		this.commands.add(new Help());
		this.commands.add(new Toggle());
		this.commands.add(new Bind());
		this.commands.add(new VClip());
		this.commands.add(new ModList());
		//this.commands.add(new CommandIRC());
		this.commands.add(new Config());
		this.commands.add(new Xraycmd());
		this.commands.add(new ClientName());
		EventBus.getInstance().register(this);
	}

	public List<Command> getCommands() {
		return this.commands;
	}

	public static Optional<Command> getCommandByName(String name) {
		return CommandManager.commands.stream().filter(c2 -> {
			boolean isAlias = false;
			String[] arrstring = c2.getAlias();
			int n = arrstring.length;
			int n2 = 0;
			while (n2 < n) {
				String str = arrstring[n2];
				if (str.equalsIgnoreCase(name)) {
					isAlias = true;
					break;
				}
				++n2;
			}
			if (!c2.getName().equalsIgnoreCase(name) && !isAlias) {
				return false;
			}
			return true;
		}).findFirst();
	}

	public void add(Command command) {
		this.commands.add(command);
	}
}
