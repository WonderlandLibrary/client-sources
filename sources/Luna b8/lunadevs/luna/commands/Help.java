package lunadevs.luna.commands;

import lunadevs.luna.command.Command;
import lunadevs.luna.main.Luna;

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
		return "-help";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		Luna.addChatMessage("-t <module>");
		Luna.addChatMessage("-bind set/del <module>");
		Luna.addChatMessage("-killaura <block/friend/lock/speed/range> §7<true/false>");
		Luna.addChatMessage("-killaura <tick/switch>");
		Luna.addChatMessage("-vclip <blocks>");
		Luna.addChatMessage("-hclip <blocks>");
		Luna.addChatMessage("-friend add/del <name>");
		Luna.addChatMessage("-longjump <old/new>");
        Luna.addChatMessage("-timer speed <value>");
        Luna.addChatMessage("-build");
        Luna.addChatMessage("-clearchat");
        
	}

}