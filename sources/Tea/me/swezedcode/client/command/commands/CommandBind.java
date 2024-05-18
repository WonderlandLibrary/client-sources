package me.swezedcode.client.command.commands;

import java.util.List;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.ModuleUtils;

public class CommandBind extends Command {

	@Override
	public void executeMsg(String[] args) {
		if (args.length >= 2) {
			if (args[0].equalsIgnoreCase("add")) {
				if (args.length == 3) {
					String moduleName = args[1];
					String key = args[2];

					if (ModuleUtils.getModuleFromName(moduleName) == null) {
						error("Module '" + moduleName + "' not found.");
						return;
					}

					if (Keyboard.getKeyIndex(key.toUpperCase()) == 0) {
						error("Invalid key!");
						return;
					}

					Module module = ModuleUtils.getModuleFromName(moduleName);

					module.setKeycode(Keyboard.getKeyIndex(key.toUpperCase()));

					msg("Set key for '" + module.getName() + "' to '" + key + "'");

					Manager.getManager().getFileManager().saveKeys();

				} else {
					error("Invalid args! Usage : 'Bind add [module] [key]' or 'Bind remove [module]'");
				}
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (args.length == 2) {
					String moduleName = args[1];

					if (ModuleUtils.getModuleFromName(moduleName) == null) {
						error("Module '" + moduleName + "' not found.");
						return;
					}

					Module module = ModuleUtils.getModuleFromName(moduleName);

					module.setKeycode(0);

					msg("Removed bind for '" + module.getName() + "'");

					Manager.getManager().getFileManager().saveKeys();

				} else {
					error("Invalid args! Usage : 'Bind add [module] [key]' or 'Bind remove [module]'");
				}
			}
		} else {
			error("Invalid args! Usage: 'Bind add [module] [key]' or 'Bind §cremove [module]'");
		}
	}

	@Override
	public String getName() {
		return "bind";
	}

}
