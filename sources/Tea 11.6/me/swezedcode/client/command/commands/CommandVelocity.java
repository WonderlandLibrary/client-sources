package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.module.modules.Fight.KillAura;
import me.swezedcode.client.module.modules.Fight.Velocity;

public class CommandVelocity extends Command {
	
	@Override
	public void executeMsg(String[] args) {
		if (args.length >= 1) {
			if ((args[0].equalsIgnoreCase("kb") || (args[0].equalsIgnoreCase("knockback")))) {
				Double percentage = null;
				try {
					percentage = Double.valueOf(Integer.parseInt(args[1]));
				} catch (NumberFormatException e) {
					return;
				}
				Velocity.percentage = percentage.intValue();
				msg("Velocity percentage has been set to §b" + String.valueOf(percentage));
			}
		}else{
			error("Invalid usage: -velocity <kb/knockback> <value>");
		}
	}

	@Override
	public String getName() {
		return "velocity";
	}
	
}
