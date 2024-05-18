package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.player.Spammer;

import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.TextComponentString;

public class CmdImpersonate extends Command {
	public CmdImpersonate() {
		super("impersonate");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if (args[0].equalsIgnoreCase("chat")) {
				String s1 = args[1];
				String s2 = "";
				for (int i = 2; i < args.length; i++) {
					s2 += args[i] + " ";
				}
				Wrapper.getPlayer().addChatMessage(new TextComponentString(Client.wrap(String.format("<" + s1 + "> " + s2), 100)));
			}
			if (args[0].equalsIgnoreCase("whisper")) {
				String s1 = args[1];
				String s2 = "";
				for (int i = 2; i < args.length; i++) {
					s2 += args[i] + " ";
				}
				Wrapper.getPlayer().addChatMessage(new TextComponentString(Client.wrap(String.format("\247d" + s1 + " whispers: " + s2), 100)));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			Wrapper.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Impersonate a player in the chat.";
	}

	@Override
	public String getSyntax() {
		return "impersonate <chat/whisper> <name> <msg>";
	}
}
