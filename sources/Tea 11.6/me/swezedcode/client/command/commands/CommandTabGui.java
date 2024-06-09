package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.manager.managers.file.files.BlurEnabled;
import me.swezedcode.client.manager.managers.file.files.TabGuiEnabled;

public class CommandTabGui extends Command {

	public static boolean on = true;

	@Override
	public void executeMsg(String[] args) {
		if (args.length == 0) {
			error("Invalid arguemnts: -tabgui <on/off>");
		}
		if (args[0].equalsIgnoreCase("on")) {
			on = true;
			msg("§aTabGui was enabled.");
			TabGuiEnabled.saveEnabled();
		} else if (args[0].equalsIgnoreCase("off")) {
			on = false;
			msg("§cTabGui was disabled.");
			TabGuiEnabled.saveEnabled();
		}
	}

	@Override
	public String getName() {
		return "tabgui";
	}

	public static void setOn(boolean on) {
		CommandTabGui.on = on;
	}

	public static boolean isOn() {
		return on;
	}

}
