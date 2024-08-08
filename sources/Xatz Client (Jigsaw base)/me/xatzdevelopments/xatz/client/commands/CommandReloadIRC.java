package me.xatzdevelopments.xatz.client.commands;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.irc.IrcManager;

public class CommandReloadIRC extends Command {
	
	public static IrcManager irc;

	@Override
	public void run(String[] commands) {
		irc.connect();;
	}

	@Override
	public String getActivator() {
		return ".reloadirc";
	}

	@Override
	public String getSyntax() {
		return ".reloadirc";
	}

	@Override
	public String getDesc() {
		return "Reloads IRC, use if your ip somehow changed and you dont want to restart client";
	}
}
