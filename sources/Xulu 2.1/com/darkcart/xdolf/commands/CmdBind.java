package com.darkcart.xdolf.commands;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;

public class CmdBind extends Command {
	public CmdBind() {
		super("bind");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if(args[0].equalsIgnoreCase("add")) {
				for(Module mod: Hacks.hackList) {
					if(mod.getName().replace(" ", "").equalsIgnoreCase(args[1])) {
						if(Keyboard.getKeyIndex(args[2].toUpperCase()) == 0) {
							Wrapper.addChatMessage("Invalid key.");
							return;
						}
						mod.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
						Wrapper.addChatMessage(mod.getName() + " bound to: " + Keyboard.getKeyName(mod.getKey()));
						Wrapper.getFileManager().saveKeybinds();
						break;
					}
				}
			}
			if(args[0].equalsIgnoreCase("del")) {
				for(Module mod: Hacks.hackList) {
					if(mod.getKey() == Keyboard.getKeyIndex(args[1].toUpperCase())) {
						mod.setKey(256);
						Wrapper.addChatMessage("Unbound: " + args[1].toUpperCase());
						Wrapper.getFileManager().saveKeybinds();
						break;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			Wrapper.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Binds a key to a hack";
	}

	@Override
	public String getSyntax() {
		return "bind add <hack> <key>, bind del <key>";
	}
}
