package me.xatzdevelopments.command.commands;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.command.Command;
import me.xatzdevelopments.modules.Module;

public class Info extends Command {

	public Info() {
		super("Info", "Shows info xD", "info", "i");
	}

	@Override
	public void onCommand(String[] args, String command) {
		Xatz.addChatMessage("v" + Xatz.version + " by " + Xatz.author);
	}
	
}
