package us.loki.freaky.command.commands;

import us.loki.legit.Client;
import us.loki.freaky.command.*;

public class Help extends Command{

	@Override
	
	public String getAlias() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "Find out about the client";
	}

	@Override
	public String getSyntax() {
		return "@help";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		Client.addChatMessage("@t <module>");
		Client.addChatMessage("@bind set/del <module>");
		Client.addChatMessage("@help");
		Client.addChatMessage("@friend friend add/remove <username>");
        
	}

}