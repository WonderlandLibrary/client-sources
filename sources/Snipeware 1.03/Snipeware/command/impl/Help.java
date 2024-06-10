package Snipeware.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;

import Snipeware.command.Command;
import Snipeware.util.other.Logger;

public class Help extends Command {

	public Help() {
		super("help");
	}

	@Override
	public String usage() {
		return ".help";
	}

	@Override
	public void executeCommand(String[] commandArguments) {
		Logger.print("--------" + ChatFormatting.GRAY + "[" + ChatFormatting.AQUA + "Commands" + ChatFormatting.GRAY + "]" + ChatFormatting.WHITE + "--------");
		Logger.print(".bind");
		Logger.print(".config");
		Logger.print(".ign");
		Logger.print(".toggle");
		Logger.print(".teleport");
		Logger.print("--------------------------");
	}
}
