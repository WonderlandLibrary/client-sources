package me.protocol_client.commands.all;

import me.protocol_client.Wrapper;
import me.protocol_client.commands.Command;
import me.protocol_client.commands.CommandManager;

public class Help extends Command{

	@Override
	public String getAlias() {
		return "Help";
	}

	@Override
	public String getDescription() {
		return "Returns all commands and how to use them.";
	}

	@Override
	public String getSyntax() {
		return ".help";
	}

	@Override
	public void onCommandSent(String command, String[] args) throws Exception {
		for(Command c : CommandManager.get().commands()){
			Wrapper.tellPlayer("§9" + c.getAlias() + "§7: " + c.getDescription());
		}
	}
	
}
