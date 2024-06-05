package me.mees.remix.commands;

import me.satisfactory.base.command.*;
import me.satisfactory.base.utils.*;
import me.satisfactory.base.relations.*;

public class CommandFriend extends Command
{
    public CommandFriend() {
        super("Friend", "friend");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length != 2 && args.length != 3) {
            MiscellaneousUtil.sendInfo("Something went wrong! Please try .friend <add/remove> <name>");
        }
        else if (args.length == 2) {
            MiscellaneousUtil.addChatMessage("" + args[0]);
            if (args[0].equalsIgnoreCase("add")) {
                FriendManager.addFriendNoAlias(args[1]);
                MiscellaneousUtil.sendInfo("Successfully added '" + args[1] + "' as friend!");
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                FriendManager.removeFriend(args[1]);
                MiscellaneousUtil.sendInfo("Successfully removed '" + args[1] + " as friend!");
            }
            else {
                MiscellaneousUtil.sendInfo("Something went wrong! Please try .friend <add/remove> <name>");
            }
        }
        else {
            FriendManager.addFriend(args[1], args[2]);
            MiscellaneousUtil.sendInfo("Successfully added '" + args[1] + "' as friend! He/She will now be known as '" + args[2] + "'");
        }
    }
}
