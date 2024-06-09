package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.module.modules.Fight.KillAura;
import me.swezedcode.client.module.modules.Visual.NameProtect;

public class CommandNameProt extends Command {

	@Override
	public void executeMsg(String[] args) {
		if (args.length >= 1) {
			if ((args[0].equalsIgnoreCase("set"))) {
				String name = null;
				try {
					name = String.valueOf((args[1]));
				} catch (NumberFormatException e) {
					return;
				}
				NameProtect.name = name.toString();
				msg("You'er name was set to §b" + String.valueOf(name));
			}
		}else{
			error("Invalid usage: -nameprotect set <string>");
		}
	}

	@Override
	public String getName() {
		return "nameprotect";
	}

}
