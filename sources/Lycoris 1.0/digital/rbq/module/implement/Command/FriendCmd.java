package digital.rbq.module.implement.Command;

import net.minecraft.util.EnumChatFormatting;
import digital.rbq.Lycoris;
import digital.rbq.module.Command;
import digital.rbq.utility.ChatUtils;

@Command.Info(name = "friend", syntax = { "add <name> | del <name> | clear | list" }, help = "Edit friends list")
public class FriendCmd extends Command {
	@Override
	public void execute(String[] args) throws Error {
		if (args.length > 2) {
			this.syntaxError();
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("add")) {
				Lycoris.INSTANCE.getFriendManager().addFriend(args[1]);
				ChatUtils.sendMessageToPlayer("Added " + EnumChatFormatting.GOLD + args[1] + EnumChatFormatting.RESET + " to friends");
			} else if (args[0].equalsIgnoreCase("del")) {
				Lycoris.INSTANCE.getFriendManager().delFriend(args[1]);
				ChatUtils.sendMessageToPlayer("Removed " + EnumChatFormatting.GOLD + args[1] + EnumChatFormatting.RESET +  " from friends");
			} else {
				this.syntaxError();
			}

		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")) {
				ChatUtils.sendMessageToPlayer("Current friends:");
				for (String friend : Lycoris.INSTANCE.getFriendManager().getFriends()) {
					ChatUtils.sendMessageToPlayer(friend);
				}
			}
			else if (args[0].equalsIgnoreCase("clear")) {
				for (String friend : Lycoris.INSTANCE.getFriendManager().getFriends())
					Lycoris.INSTANCE.getFriendManager().delFriend(friend);

				ChatUtils.sendMessageToPlayer("Cleared friends");
			}else {
				this.syntaxError();
			}
		} else {
			this.syntaxError();
		}
	}
}
