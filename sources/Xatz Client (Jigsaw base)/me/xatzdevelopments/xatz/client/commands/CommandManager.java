package me.xatzdevelopments.xatz.client.commands;

import java.util.ArrayList;

public class CommandManager {

	public ArrayList<Command> commands = new ArrayList<Command>();

	public CommandManager() {
		commands.add(new CommandHelp());
		commands.add(new CommandSay());
		commands.add(new CommandBind());
		
		commands.add(new CommandToggle());
		commands.add(new CommandVclip());
		commands.add(new CommandDamage());
		commands.add(new CommandFakehacker());
		commands.add(new CommandNameprotect());
		commands.add(new CommandCommands());
		commands.add(new CommandBleach());
		//commands.add(new CommandClickGuiSize());
		commands.add(new CommandCrasher());
		commands.add(new CommandSpider());
		//commands.add(new CommandGetIp());
		//commands.add(new CommandGeoIp());
		//commands.add(new CommandGetIpAll());
		//commands.add(new CommandGeoPlayer());
		commands.add(new CommandPlayerHead());
		commands.add(new CommandReloadIRC());
		commands.add(new CommandCleanRam());
		commands.add(new CommandNBTViewer());
		//commands.add(new CommandBugUp());
		commands.add(new CommandBook());
		//commands.add(new CommandPlayMusic());
		commands.add(new CommandSongs());
		commands.add(new CommandMotionStand());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		//commands.add(new PlaceHolder());
		commands.add(new CommandKickall());
		commands.add(new CommandDeathStick());
		commands.add(new CommandHastebin());
		commands.add(new CommandInvsee());
		commands.add(new CommandArmorStandRain());
		commands.add(new CommandFriend());
		commands.add(new CommandServerVersion());
		commands.add(new CommandFakeMessage());
	}

	public boolean onCommand(String activator, String[] commands) {
		for (Command cmd : this.commands) {
			if (cmd.getActivator().equals(activator)) {
				cmd.run(commands);
				return true;
			}
		}
		return false;
	}

}
