package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;

public class CommandShit extends Command {

	public static boolean toggled = false;

	@Override
	public void executeMsg(String[] args) {
		if (args.length == 0) {
			toggled = !toggled;
			if (toggled) {
				msg("§2Sheeit");
			} else {
				msg("§cSheeit");
			}
		}
	}

	@Override
	public String getName() {
		return "shit";
	}

}
