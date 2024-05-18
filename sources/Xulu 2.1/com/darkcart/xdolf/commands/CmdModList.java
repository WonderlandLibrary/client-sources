package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;

import net.minecraft.util.text.TextComponentString;

public class CmdModList extends Command
{
	public CmdModList()
	{
		super("modlist");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		for(Module mod: Hacks.hackList)
		{
			if(mod.getDescription() != "") {
				Wrapper.addChatMessage(mod.getName().replace("<", "<\247a").replace(">", "\247f>") + " - " + mod.getDescription());
			}
		}
	}

	@Override
	public String getDescription()
	{
		return "Lists all modules.";
	}

	@Override
	public String getSyntax()
	{
		return "modlist";
	}
}