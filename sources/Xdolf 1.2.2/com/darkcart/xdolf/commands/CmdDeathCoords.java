package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Wrapper;

import net.minecraft.client.gui.GuiGameOver;

public class CmdDeathCoords extends Command {

	public CmdDeathCoords() {
		super("deathcoords");
	}

	@Override
	public void runCommand(String s, String[] args) {
		Wrapper.addChatMessage("You died at X: " + GuiGameOver.deathX + ", Y: " + GuiGameOver.deathY + ", Z: " + GuiGameOver.deathZ);
	}

	@Override
	public String getDescription() {
		return "Tells you where you last died.";
	}

	@Override
	public String getSyntax() {
		return "deathcoords";
	}

}
