package us.loki.freaky.command.commands;

import org.lwjgl.input.Keyboard;

import us.loki.legit.Client;
import us.loki.freaky.command.*;
import us.loki.legit.modules.*;

public class Bind extends Command{

	@Override
	public String getAlias() {
		return "bind";
	}

	@Override
	public String getDescription() {
		return "Allows user to change keybind of module";
	}

	@Override
	public String getSyntax() {
		return "@bind set [MOD] [Key] | .bind del [MOD]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0].equalsIgnoreCase("set")){
			args[2] = args[2].toUpperCase();
			int key = Keyboard.getKeyIndex(args[2]);
			
			for(Module m: ModuleManager.getModules()){
				if(args[1].equalsIgnoreCase(m.getName())){
					m.setKeybind(Keyboard.getKeyIndex(Keyboard.getKeyName(key)));
					Client.addChatMessage(args[1] + " has been binded to " + args[2]);
				}
			}
			
		}else if(args[0].equalsIgnoreCase("del")){
			for(Module m: ModuleManager.getModules()){
				if(m.getName().equalsIgnoreCase(args[1])){
					m.setKeybind(0);
					Client.addChatMessage(args[1] + " has been unbinded");
				}
			}
	}
	}
}