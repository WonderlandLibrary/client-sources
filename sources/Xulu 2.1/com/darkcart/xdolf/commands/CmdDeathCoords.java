package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.player.DeathCoords;

public class CmdDeathCoords extends Command {

	public CmdDeathCoords() {
		super("deathcoords");
	}

	@Override
	public void runCommand(String s, String[] args) {
		Wrapper.addChatMessage("You died at X: " + DeathCoords.x + ", Y: " + DeathCoords.y + ", Z: " + DeathCoords.z);
	}

	@Override
	public String getDescription() {
		return "Tells you where you last died. Works in conjunction with DeathCoords mod";
	}

	@Override
	public String getSyntax() {
		return "deathcoords";
	}

}