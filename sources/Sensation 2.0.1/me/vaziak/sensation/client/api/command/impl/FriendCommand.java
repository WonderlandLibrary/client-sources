package me.vaziak.sensation.client.api.command.impl;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.command.Command;
import me.vaziak.sensation.client.api.friend.Friend;
import me.vaziak.sensation.utils.client.ChatUtils;
import net.minecraft.util.EnumChatFormatting;

public class FriendCommand extends Command {
    public FriendCommand() {
        super("friend", "Adds or removes a player to not hit", "<add/remove> <player> <alias>", new String[]{"f"});
    }

    @Override
    public boolean onCommand(String[] args) {
        if (args.length >= 2) {
            String argument = args[1];

            if (argument.equalsIgnoreCase("add")) {
                if (args.length == 4) {
                    Sensation.instance.friendManager.addFriend(new Friend(args[2], args[3]));
                    ChatUtils.log("Added " + EnumChatFormatting.RED + args[2]);
                    return true;
                } else if (args.length == 3) {
                    Sensation.instance.friendManager.addFriend(new Friend(args[2], args[2]));
                    ChatUtils.log("Added " + EnumChatFormatting.RED + args[2]);
                    return true;
                }
            } else if (argument.equalsIgnoreCase("remove")) {
                if (args.length == 3) {
                    String name = args[2];

                    Friend friend = Sensation.instance.friendManager.getFriend(name);

                    if (friend == null) {
                        ChatUtils.log("Could not find friend");
                        return true;
                    }

                    Sensation.instance.friendManager.removeFriend(friend);
                    ChatUtils.log("Removed " + EnumChatFormatting.RED + name);
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}