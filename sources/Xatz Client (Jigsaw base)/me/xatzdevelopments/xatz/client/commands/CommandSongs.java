package me.xatzdevelopments.xatz.client.commands;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.utils.Wrapper;

public class CommandSongs extends Command {

	@Override
	public void run(String[] commands) {
    	Wrapper.tellPlayer("Current songs that are built in, Closer, SilentPartnerSpaceWalk, TheseDays.");
    	Wrapper.tellPlayer("(I will add a better song system in the future, don't judge its my first ever client)");
	}

	@Override
	public String getActivator() {
		return ".songs";
	}

	@Override
	public String getSyntax() {
		return ".songs";
	}

	@Override
	public String getDesc() {
		return "Get a list of built in client songs (Copyright strike = Gay)";
	}
}
