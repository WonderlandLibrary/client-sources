package me.xatzdevelopments.command.commands;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.command.Command;
import me.xatzdevelopments.modules.Module;

public class Bind extends Command {

	public Bind() {
		super("Bind", "Binds a module lel", "bind <name> <key> | clear", "b");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if(args.length == 2) {
			String moduleName = args[0];
			String keyName = args[1];
			
			boolean foundModule = false;
			
			for(Module module : Xatz.modules) {
				if(module.name.equalsIgnoreCase(moduleName)) {
					module.keycode.setKeyCode(Keyboard.getKeyIndex(keyName.toUpperCase()));
					
					Xatz.addChatMessage(String.format("Bound %s to %s", module.name, Keyboard.getKeyName(module.getKey())));
					foundModule = true;
					break;
				}
			}
			
			if(!foundModule) {
				Xatz.addChatMessage("Couldn't find module");
			}
		}
		
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("clear")) {
				for(Module module :Xatz.modules) {
					module.keycode.setKeyCode(Keyboard.KEY_NONE);
				}
			}
			
			Xatz.addChatMessage("Cleared all binds");
		}
	}
	
}
