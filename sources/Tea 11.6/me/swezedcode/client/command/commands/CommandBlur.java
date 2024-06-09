package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.manager.managers.file.files.BlurEnabled;

public class CommandBlur extends Command {

	public static boolean on = true;

	@Override
	public void executeMsg(String[] args) {
		if (args.length == 0) {
			error("Invalid arguemnts: -blur <on/off>");
		}
		if (args[0].equalsIgnoreCase("on")) {
			on = true;
			msg("§aBlur was enabled.");
			BlurEnabled.saveEnabled();
		} else if (args[0].equalsIgnoreCase("off")) {
			on = false;
			msg("§cBlur was disabled.");
			BlurEnabled.saveEnabled();
		}
	}

	@Override
	public String getName() {
		return "blur";
	}

	public static void setOn(boolean on) {
		CommandBlur.on = on;
	}

	public static boolean isOn() {
		return on;
	}

}
