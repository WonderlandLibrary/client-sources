package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Wrapper;

public class CmdRotation extends Command {

	public CmdRotation() {
		super("rotate");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			Wrapper.getPlayer().rotationYaw = Integer.parseInt(args[0]);
			Wrapper.getPlayer().rotationPitch = Integer.parseInt(args[1]);
		}catch(Exception ex) {
			ex.printStackTrace();
			Wrapper.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Rotates the player. Useful for autowalking.";
	}

	@Override
	public String getSyntax() {
		return "rotate <yaw> <pitch>";
	}

}
