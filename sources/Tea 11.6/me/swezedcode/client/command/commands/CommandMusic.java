package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.module.modules.Fight.KillAura;
import me.swezedcode.client.module.modules.Options.Music;

public class CommandMusic extends Command {

	@Override
	public void executeMsg(String[] args) {
		if (args.length >= 1) {
			if ((args[0].equalsIgnoreCase("volume"))) {
				Float volume = null;
				try {
					volume = Float.valueOf(Float.parseFloat(args[1]));
				} catch (NumberFormatException e) {
					return;
				}
				Music.volume = volume.floatValue();
				msg("Music volume was set to " + String.valueOf(volume) + ", please re-toggle after set. §7(Defualt value: -28.0)");
			}
		}else{
			error("Invalid usage: -music volume <floatvalue>");
		}
	}

	@Override
	public String getName() {
		return "music";
	}
	
}
