package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.manager.Manager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class CommandFriend extends Command {
	
	public static String name2;
	
	@Override
	public void executeMsg(String[] args) {
		if (args.length >= 1) {
			try {
				String name;
				if (args[0].equalsIgnoreCase("add")) {
					name = args[1];
					name2 = args[2];
					if(args[2] == null) {
						error("Invalid args! Usage: Friend add <player> <alias>");
					}
					if (!Manager.getManager().getFriendManager().isFriend(name)) {
						Manager.getManager().getFriendManager().addFriend(name, name2);
						msg("§fFriend added to list:§b" + name + "§f.");
					} else {
						msg("§b" + name + " §fis already your friend.");
					}
				}
				if (args[0].equalsIgnoreCase("del")) {
					name = args[1];
					if (Manager.getManager().getFriendManager().isFriend(name)) {
						Manager.getManager().getFriendManager().removeFriend(name);
						msg("§fRemoved §b" + name + " §ffrom friend list.");
					} else {
						msg("§fRemoved §b" + name + " §fwas not found as friend.");
					}
				}
			} catch (Exception friend) {
				;
			}
		}else{
			error("Invalid args! Usage: 'Friend add <player> <alias>' or 'Friend §cdel §c<player>'");
		}
	}

	@Override
	public String getName() {
		return "friend";
	}
}
