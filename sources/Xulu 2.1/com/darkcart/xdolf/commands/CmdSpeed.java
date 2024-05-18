package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.movement.yPort;

public class CmdSpeed extends Command {
	public CmdSpeed() {
		super("speed");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if(args[0].equalsIgnoreCase("yport")){
				yPort.mode=1;
				Wrapper.addChatMessage("§7Speed mode changed to: yPort");

		}
			if(args[0].equalsIgnoreCase("timer")){
			    yPort.mode = 0;
			    Wrapper.addChatMessage("§7Speed mode changed to: Timer");

		}
		} catch(Exception e) {
			e.printStackTrace();
			Wrapper.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Changes speed mode.";
	}

	@Override
	public String getSyntax() {
		return "speed <yport/timer>";
	}
}
