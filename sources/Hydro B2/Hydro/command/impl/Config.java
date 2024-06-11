package Hydro.command.impl;

import java.util.List;

import Hydro.Client;
import Hydro.command.CommandExecutor;
import Hydro.util.ChatUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class Config implements CommandExecutor {

	@Override
	public void execute(EntityPlayerSP sender, List<String> args) {
		if (args.size() >= 1) {
            String upperCaseFunction = args.get(0).toUpperCase();
            
            if (args.size() == 2) {
                switch (upperCaseFunction) {
                    case "LOAD":
                        if (Client.instance.configManager.loadConfig(args.get(1)))
                            ChatUtils.sendMessageToPlayer("Successfully loaded config: '" + args.get(1) + "'");
                        else
                            ChatUtils.sendMessageToPlayer("Failed to load config: '" + args.get(1) + "'");
                        break;
                    case "SAVE":
                        if (Client.instance.configManager.saveConfig(args.get(1)))
                            ChatUtils.sendMessageToPlayer("Successfully saved config: '" + args.get(1) + "'");
                        else
                            ChatUtils.sendMessageToPlayer("Failed to save config: '" + args.get(1) + "'");
                        break;
                    case "DELETE":
                        if (Client.instance.configManager.deleteConfig(args.get(1)))
                            ChatUtils.sendMessageToPlayer("Successfully deleted config: '" + args.get(1) + "'");
                        else
                            ChatUtils.sendMessageToPlayer("Failed to delete config: '" + args.get(1) + "'");
                        break;
                }
            } else if (args.size() == 1 && upperCaseFunction.equalsIgnoreCase("LIST")) {
                if(Client.instance.configManager.getContents().size() == 0) {
                	ChatUtils.sendMessageToPlayer("No configs available!");
                }else {
                	ChatUtils.sendMessageToPlayer("--Available Configs--");
                    for (Hydro.config.Config config : Client.instance.configManager.getContents())
                        ChatUtils.sendMessageToPlayer(config.getName());
                }
            }
        }
	}

	
	
}
