package me.protocol_client.commands.all;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.commands.Command;

public class Friend extends Command {

	@Override
	public String getAlias() {
		return "Friend";
	}

	@Override
	public String getDescription() {
		return "Add or delete people as friends.";
	}

	@Override
	public String getSyntax() {
		return ".friend add <name> <alias> | .friend del <name/alias>";
	}

	@Override
	public void onCommandSent(String command, String[] args) throws Exception {
		if (args[0].equalsIgnoreCase("add")) {
			String name = args[1];
			String alias = args[2];
			if (!Protocol.getFriendManager().isFriend(name)) {
				Protocol.getFriendManager().addFriend(name, alias);
				Wrapper.tellPlayer("§7Added " + "§9" + name + " §7as a friend");
			} else {
				Wrapper.tellPlayer("§9" + name + " §7is already your friend");
			}
		}
		else if (args[0].equalsIgnoreCase("del")) {
			String name = args[1];
			if (Protocol.getFriendManager().isFriend(name)) {
				Protocol.getFriendManager().removeFriend(name);
				Wrapper.tellPlayer("§7Removed " + "§9" + name + " §7from your friend list");
			} else {
				Wrapper.tellPlayer("§9" + name + " §7is not your friend");
			}
		}else{
			Float f = Float.parseFloat(args[544]);
		}
	}

}
