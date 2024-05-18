package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Wrapper;

public class CmdHelp extends Command
{
	public CmdHelp()
	{
		super("help");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		for(Command cmd: CommandManager.commands)
		{
			if(cmd != this) {
				Wrapper.addChatMessage(cmd.getSyntax().replace("§7<§a", "§7<§a").replace("§7>", "§7>§a") + " - " + cmd.getDescription());
			}
		}
	}

	@Override
	public String getDescription()
	{
		return "Lists all commands";
	}

	@Override
	public String getSyntax()
	{
		return "help";
	}
}