package Focus.Beta.IMPL.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Focus.Beta.API.EventBus;
import Focus.Beta.API.commands.Command;
import Focus.Beta.API.commands.impl.Bind;
import Focus.Beta.API.commands.impl.Client;
import Focus.Beta.API.commands.impl.Config;

public class CommandManager implements Manager {
	private static List<Command> commands;

	@Override
	public void init() {

		this.commands = new ArrayList<Command>();
		commands.add(
				new Bind()
		);
		commands.add(new Client());
		commands.add(new Config());
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