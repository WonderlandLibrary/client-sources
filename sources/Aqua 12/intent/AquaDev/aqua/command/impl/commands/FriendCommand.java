// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.command.impl.commands;

import intent.AquaDev.aqua.utils.FriendSystem;
import intent.AquaDev.aqua.utils.ChatUtil;
import intent.AquaDev.aqua.command.Command;

public class FriendCommand extends Command
{
    public FriendCommand() {
        super("friend");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length != 2) {
            ChatUtil.sendChatError("Please use: §e.friend <add | remove> <Player> §7| §e.friend <list>");
            return;
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (!FriendSystem.getFriends().contains(args[1])) {
                FriendSystem.addFriend(args[1]);
                ChatUtil.sendChatMessageWithPrefix("§aSuccessfully added " + args[1]);
                ChatUtil.messageWithoutPrefix(FriendSystem.getFriends().toString());
            }
            else {
                ChatUtil.sendChatError("Player already added.");
            }
        }
        else if (args[0].equalsIgnoreCase("remove")) {
            if (FriendSystem.getFriends().contains(args[1])) {
                FriendSystem.removeFriend(args[1]);
                ChatUtil.sendChatMessageWithPrefix("§aSuccessfully removed " + args[1]);
            }
            else {
                ChatUtil.sendChatError("Player not added");
            }
        }
        else if (args[0].equalsIgnoreCase("list")) {
            ChatUtil.sendChatMessageWithPrefix("§5" + FriendSystem.getFriends().toString());
        }
        else {
            ChatUtil.sendChatError("Please use: §e.friend <add | remove> <Player> §7| §e.friend <list>");
        }
    }
}
