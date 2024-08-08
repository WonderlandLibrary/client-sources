package me.xatzdevelopments.command.commands;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.command.Command;
import me.xatzdevelopments.modules.Module;

public class Toggle extends Command {

	public Toggle() {
		super("Toggle", "Toggles a module lel", "toggle <name>", "t");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if(args.length > 0) {
			String moduleName = args[0];
			
			boolean foundModule = false;
			
			for(Module module : Xatz.modules) {
				if(module.name.equalsIgnoreCase(moduleName)) {
					module.toggle();
					
					Xatz.addChatMessage((module.isEnabled() ? "Enabled" : "Disabled") + " " + module.name);
					
					foundModule = true;
					break;
				}
			}
			
			if(!foundModule) {
				Xatz.addChatMessage("Couldn't find this module");
			}
		}
	}
	
}
