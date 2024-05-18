package com.darkcart.xdolf.commands;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Macro;

public class CmdMacro extends Command {
	public CmdMacro() {
		super("macro");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if(args[0].equalsIgnoreCase("add")) {
				if(Keyboard.getKeyIndex(args[1].toUpperCase()) == 0) {
					Wrapper.addChatMessage("Invalid key.");
					return;
				}
				Macro m = new Macro(Keyboard.getKeyIndex(args[1].toUpperCase()), s.substring(11 + args[1].length()));
				Wrapper.addChatMessage("Added \"" + m.getCommand() + "\" on key: " + Keyboard.getKeyName(m.getKey()));
				Wrapper.getFileManager().saveMacros();
			}
			if(args[0].equalsIgnoreCase("del")) {
				if(Keyboard.getKeyIndex(args[1].toUpperCase()) == 0) {
					Wrapper.addChatMessage("Invalid key.");
					return;
				}
				for(Macro m : Macro.macroList) {
					if(Keyboard.getKeyName(m.getKey()).equalsIgnoreCase(args[1].toUpperCase())) {
						Macro.macroList.remove(m);
						Wrapper.addChatMessage("Removed macro on key: " + Keyboard.getKeyName(m.getKey()));
						Wrapper.getFileManager().saveMacros();
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
		return "Adds and removes macros.";
	}

	@Override
	public String getSyntax() {
		return "macro add <key> <command>, macro del <key>";
	}
}
