package me.gishreload.yukon.commands;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.command.Command;
import me.gishreload.yukon.module.Module;


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
		return "\u00a74.bind add [Чит] [Клавиша] | .bind del [Чит] | .bind clear";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0].equalsIgnoreCase("add")){
			args[2] = args[2].toUpperCase();
			int key = Keyboard.getKeyIndex(args[2]);
			
			for(Module m: Edictum.getModules()){
				if(args[1].equalsIgnoreCase(m.getName())){
					m.setKey(Keyboard.getKeyIndex(Keyboard.getKeyName(key)));
					Edictum.addChatMessage("\u00a76" + args[1] + "\u00a7a" + " забинджено на "+"\u00a76" + args[2]);
				}
			}
			
		}else if(args[0].equalsIgnoreCase("del")){
			for(Module m: Edictum.getModules()){
				if(m.getName().equalsIgnoreCase(args[1])){
					m.setKey(0);
					Edictum.addChatMessage("\u00a76" + args[1] + " \u00a7aРазбинджено \u00a76"+ args[2]);
				}
			}
		}else if(args[0].equalsIgnoreCase("clear")){
			for(Module m: Edictum.getModules()){
				m.setKey(0);
			}
			Edictum.addChatMessage("\u00a73Все бинды сняты.");
		}
	}

}
