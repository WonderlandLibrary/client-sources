package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.module.modules.Fight.KillAura;

public class CommandAura extends Command {

	public static Integer delay = null;
	
	@Override
	public void executeMsg(String[] args) {
		if (args.length >= 1) {
			if ((args[0].equalsIgnoreCase("delay")) || (args[0].equalsIgnoreCase("speed")
					|| (args[0].equalsIgnoreCase("cps") || (args[0].equalsIgnoreCase("aps"))))) {
				try {
					delay = Integer.valueOf(Integer.parseInt(args[1]));
				} catch (NumberFormatException e) {
					return;
				}
				KillAura.delay = (delay.intValue());
				msg("Aura speed now set to §b" + String.valueOf(delay));
			}
			if ((args[0].equalsIgnoreCase("range")) || (args[0].equalsIgnoreCase("reach"))) {
				Float reach = null;
				try {
					reach = Float.valueOf(Float.parseFloat(args[1]));
				} catch (NumberFormatException e) {
					return;
				}
				KillAura.reach = (reach.floatValue());
				msg("Aura range now set to §b" + String.valueOf(reach));
			}
			if ((args[0].equalsIgnoreCase("randomness")) || (args[0].equalsIgnoreCase("random"))) {
				Integer rnd = null;
				try {
					rnd = Integer.valueOf(Integer.parseInt(args[1]));
				} catch (NumberFormatException e) {
					return;
				}
				KillAura.rndDelay = (rnd.floatValue());
				msg("Aura random delay now set to §b" + String.valueOf(rnd));
			}
			if ((args[0].equalsIgnoreCase("mode"))) {
				String mode = null;
				try {
					mode = String.valueOf((args[1]));
				} catch (NumberFormatException e) {
					return;
				}
				if (mode.equalsIgnoreCase("Switch") || mode.equalsIgnoreCase("Advanced") || mode.equalsIgnoreCase("Tick")) {
					KillAura.mode = args[1];
					msg("Aura mode set to §b" + args[1]);
				}
			}
		} else {
			error("Invalid usage: -aura <range/delay/randomness/mode> <value>");
		}
	}

	@Override
	public String getName() {
		return "aura";
	}

}
