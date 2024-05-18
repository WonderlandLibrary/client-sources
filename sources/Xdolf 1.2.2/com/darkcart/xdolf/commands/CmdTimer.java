package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.world.Timer;

import net.minecraft.util.text.TextComponentString;

public class CmdTimer extends Command {

	public CmdTimer() {
		super("timer");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			Timer.speed = Integer.parseInt(args[0]);
			Wrapper.addChatMessage("Timer updated.");
		}catch(Exception ex) {
			ex.printStackTrace();
			Wrapper.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Change timer speed.";
	}

	@Override
	public String getSyntax() {
		return "timer <speed>";
	}

}
