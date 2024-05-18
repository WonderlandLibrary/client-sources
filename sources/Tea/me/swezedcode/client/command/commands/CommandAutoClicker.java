package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.module.modules.Fight.AutoClicker;
import me.swezedcode.client.module.modules.Fight.KillAura;

public class CommandAutoClicker extends Command {

	@Override
	public void executeMsg(String[] args) {
		if (args.length >= 1) {
			if ((args[0].equalsIgnoreCase("average"))) {
				Integer delay = null;
				try {
					delay = Integer.valueOf(Integer.parseInt(args[1]));
				} catch (NumberFormatException e) {
					return;
				}
				AutoClicker.AveregeCPS = (delay.intValue());
				msg("Average average now set to §b" + String.valueOf(delay));
			}
		} else {
			error("Invalid usage: -autoclicker cps <value>");
		}
	}

	@Override
	public String getName() {
		return "aura";
	}

}