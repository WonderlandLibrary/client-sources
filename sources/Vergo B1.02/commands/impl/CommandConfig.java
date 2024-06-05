package xyz.vergoclient.commands.impl;

import xyz.vergoclient.Vergo;
import xyz.vergoclient.commands.CommandManager;
import xyz.vergoclient.commands.OnCommandInterface;
import xyz.vergoclient.modules.ModuleManager;
import xyz.vergoclient.util.main.ChatUtils;

public class CommandConfig implements OnCommandInterface {

	@Override
	public void onCommand(String... args) {
		
		if (args.length < 3) {
			argsWarn();
			return;
		}
		
		String configName = args[2];
		
		if (args[1].equalsIgnoreCase("save")) {
			Vergo.config.save(configName);
			ChatUtils.addChatMessage("Config saved");
		}
		else if (args[1].equalsIgnoreCase("load")) {
			ModuleManager.getConfig(configName);
			ChatUtils.addChatMessage("Config loaded");
		}
		else
			argsWarn();
		
	}
	
	private void argsWarn() {
		ChatUtils.addChatMessage("Please use " + getUsage());
	}
	
	@Override
	public String getName() {
		return "config";
	}

	@Override
	public String getUsage() {
		return CommandManager.prefix + getName() + " <save/load> <config>";
	}

	@Override
	public String getDescription() {
		return "Saves or loads a config";
	}

}
