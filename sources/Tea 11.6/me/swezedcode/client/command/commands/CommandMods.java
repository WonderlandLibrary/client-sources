package me.swezedcode.client.command.commands;

import java.io.Console;
import java.util.List;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.ModuleManager;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.console.ConsoleUtils;

public class CommandMods extends Command {

	@Override
	public void executeMsg(String[] args) {
		for(Module m : ModuleManager.getModules()) {
			ConsoleUtils.logChat(m.getName() + ", ");
		}
	}

	@Override
	public String getName() {
		return "mods";
	}

	
	
}
