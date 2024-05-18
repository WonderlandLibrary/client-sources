package com.darkcart.xdolf.commands;

import java.io.File;

import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.player.Spammer;

public class CmdSpam extends Command {

	public CmdSpam() {
		super("spam");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if (args[0].equalsIgnoreCase("mode")) {
				Spammer.mode = args[1];
			}
			if (args[0].equalsIgnoreCase("msg")) {
				String message = "";
				for (int i = 1; i < args.length; i++) {
					message += args[i] + " ";
				}
				Spammer.message = message;
				Wrapper.addChatMessage("Spam message changed to \247e" + Spammer.message);
			}
			if (args[0].equalsIgnoreCase("delay")) {
				Spammer.delay = Integer.parseInt(args[1]);
				Wrapper.addChatMessage("Spam delay changed to \247e" + Spammer.delay);
			}
			if (args[0].equalsIgnoreCase("file")) {
				Spammer.file = Wrapper.getFileManager().xdolfDir + File.separator + "spam" + File.separator + args[1];
				Wrapper.addChatMessage("Loaded \247e" + Spammer.file);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			Wrapper.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Configure the spam module.";
	}

	@Override
	public String getSyntax() {
		return "spam <mode/msg/delay/file> <args>";
	}

}
