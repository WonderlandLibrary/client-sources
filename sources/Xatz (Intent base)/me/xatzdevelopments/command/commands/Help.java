package me.xatzdevelopments.command.commands;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.command.Command;
import me.xatzdevelopments.command.CommandManager;
import me.xatzdevelopments.modules.Module;

public class Help extends Command {

	public Help() {
		super("Help", "Get help ya scallywag", "help", "h");
	}

	@Override
	public void onCommand(String[] args, String command) {
		for(Command commandList : CommandManager.commands) {
			Xatz.addChatMessage(commandList.getName() + " : " + commandList.getDescription());
		}
	}
	
}
