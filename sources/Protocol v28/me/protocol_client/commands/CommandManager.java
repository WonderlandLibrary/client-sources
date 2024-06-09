package me.protocol_client.commands;

import java.util.ArrayList;

import me.protocol_client.Wrapper;
import me.protocol_client.commands.all.*;

public class CommandManager {
	private ArrayList<Command>		cmds;
	private static CommandManager	me	= new CommandManager();

	public static CommandManager get() {
		return me;
	}

	public Command[] commands() {
		return new Command[] { new Help(), new VClip(), new Bind(), new Toggled(), new Damage(), new Suicide(), new Coords(), new Friend(), new Forward() };
	}

	public CommandManager() {
		cmds = new ArrayList();
		for (Command c : commands()) {
			if (c != null) {
				cmds.add(c);
			}
		}
	}

	public void runCommand(String i) {
		String[] m = i.split(" ");
		String ranCmd = m[0];
		String sent = i.substring(ranCmd.length()).trim();
		for (Command c : getCommands()) {
			if (c.getAlias().equalsIgnoreCase(ranCmd)) {
				try {
					c.onCommandSent(sent, sent.split(" "));
					return;
				} catch (Exception e) {
					Wrapper.tellPlayer("§7Invalid arguments.");
					Wrapper.tellPlayer("§7" + c.getSyntax());
				}
				return;
			}
		}
		Wrapper.tellPlayer("§7Command \"" + "§9" + ranCmd + "§7" + "\" §7couldn't be found. Type .help for help.");
	}

	private ArrayList<Command> getCommands() {
		return cmds;
	}

}
