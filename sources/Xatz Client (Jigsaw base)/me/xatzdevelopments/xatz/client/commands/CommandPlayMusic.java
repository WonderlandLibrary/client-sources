package me.xatzdevelopments.xatz.client.commands;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.gui.Level;
import me.xatzdevelopments.xatz.gui.Notification;
import me.xatzdevelopments.xatz.module.Module;
import me.xatzdevelopments.xatz.utils.Wrapper;

public class CommandPlayMusic extends Command {

	@Override
	public void run(String[] commands) {
	}
	
	@Override
	public String getActivator() {
		return ".playmusic";
	}

	@Override
	public String getSyntax() {
		return ".playmusic <song name>";
	}

	@Override
	public String getDesc() {
		return "Plays some beats";
	}
}
