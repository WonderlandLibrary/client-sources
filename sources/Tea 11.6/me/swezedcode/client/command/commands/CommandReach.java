package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.module.modules.Fight.KillAura;
import me.swezedcode.client.module.modules.Fight.Reach;

public class CommandReach extends Command {

	@Override
	public void executeMsg(String[] args) {
		if(args.length == 0) {
			error("Invalid usage: -reach dis/disctace <value>");
		}
		if(args[0].equalsIgnoreCase("dis") || args[0].equalsIgnoreCase("distance")) {
			Float reach = null;
			try {
				reach = Float.valueOf(Float.parseFloat(args[1]));
			} catch (NumberFormatException e) {
				return;
			}
			Reach.reach = reach.floatValue();
			msg("Reach distance now set to §b" + String.valueOf(reach));
		}
	}

	@Override
	public String getName() {
		return "reach";
	}
}
