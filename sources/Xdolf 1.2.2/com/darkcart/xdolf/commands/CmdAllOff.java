package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;

import net.minecraft.util.text.TextComponentString;

public class CmdAllOff extends Command
{
	public CmdAllOff() 
	{
		super("alloff");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		int count = 0;
		for(Module mod: Hacks.hackList)
		{
			if(mod.isEnabled() && !mod.getName().equalsIgnoreCase("ttf chat"))
			{
				mod.toggle();
				count++;
			}
		}
		
		Wrapper.addChatMessage(count + (count == 1 ? " hack" : " hacks") + " turned off.");
	}

	@Override
	public String getDescription()
	{
		return "Turns all hacks off.";
	}

	@Override
	public String getSyntax()
	{
		return "alloff";
	}
}
