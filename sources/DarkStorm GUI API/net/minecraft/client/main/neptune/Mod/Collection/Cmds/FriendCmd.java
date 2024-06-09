package net.minecraft.client.main.neptune.Mod.Collection.Cmds;

import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Cmd.Info;
import net.minecraft.client.main.neptune.Utils.ChatUtils;

@Info(name = "friend", syntax = { "add <name>", "del <name>, list" }, help = "Manages your friends")
public class FriendCmd extends Cmd {
	@Override
	public void execute(String[] args) throws Error {
		if (args.length > 2) {
			ChatUtils.sendMessageToPlayer("Args: add <name>, del <name>, list");
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("add")) {
				Neptune.getWinter().friendUtils.addFriend(args[1]);
			} else if (args[0].equalsIgnoreCase("del")) {
				Neptune.getWinter().friendUtils.delFriend(args[1]);
			} else {
				ChatUtils.sendMessageToPlayer("Args: add <name>, del <name>, list");
			}
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")) {
				ChatUtils.sendMessageToPlayer("Friends:");
				int i = 0;
				for (String friend : Neptune.getWinter().friendUtils.getFriends()) {
					++i;
					ChatUtils.sendMessageToPlayer(i + ". " + friend);
				}
			} else {
				ChatUtils.sendMessageToPlayer("Args: add <name>, del <name>, list");
			}
		} else {
			ChatUtils.sendMessageToPlayer("Args: add <name>, del <name>, list");
		}
	}
}
