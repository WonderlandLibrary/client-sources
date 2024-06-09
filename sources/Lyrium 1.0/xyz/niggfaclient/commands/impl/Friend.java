// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.commands.impl;

import xyz.niggfaclient.friend.Friends;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.utils.other.Printer;
import xyz.niggfaclient.commands.Command;

public class Friend extends Command
{
    public Friend() {
        super("Friend", "Friend", "", new String[] { "f" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 1 || args.length == 0) {
            Printer.addMessage("Correct Usage: .friend add <name> | .friend remove <name>");
            return;
        }
        final String s = args[1];
        switch (s) {
            case "add": {
                if (Client.getInstance().getFriendManager().isFriend(args[2])) {
                    Printer.addMessage(args[2] + " is already your friend.");
                    return;
                }
                if (args.length < 4) {
                    Printer.addMessage("Added " + args[2] + " to your friends list without alias.");
                    Printer.addMessage("Added " + args[2] + " to your friends list without an alias.");
                    Client.getInstance().getFriendManager().addFriend(args[2]);
                    break;
                }
                Printer.addMessage("Added " + args[2] + " to your friends list.");
                Printer.addMessage("Added " + args[2] + " to your friends list with the alias " + args[3] + ".");
                Client.getInstance().getFriendManager().addFriendWithAlias(args[2], args[3]);
                break;
            }
            case "remove": {
                if (!Client.getInstance().getFriendManager().isFriend(args[2])) {
                    Printer.addMessage(args[2] + " is not your friend.");
                    return;
                }
                if (Client.getInstance().getFriendManager().isFriend(args[2])) {
                    Printer.addMessage("Removed " + args[2] + " from your friends list.");
                    Client.getInstance().getFriendManager().removeFriend(args[2]);
                    break;
                }
                break;
            }
            case "clear": {
                if (Client.getInstance().getFriendManager().getFriends().isEmpty()) {
                    Printer.addMessage("Your friends list is already empty.");
                    return;
                }
                Printer.addMessage("Your have cleared your friends list. Friends removed: " + Client.getInstance().getFriendManager().getFriends().size());
                Client.getInstance().getFriendManager().clearFriends();
                break;
            }
            case "list": {
                if (Client.getInstance().getFriendManager().getFriends().isEmpty()) {
                    Printer.addMessage("Your friends list is empty.");
                    return;
                }
                Printer.addMessage("Your current friends are: ");
                String string;
                final StringBuilder sb;
                Client.getInstance().getFriendManager().getFriends().forEach(friend -> {
                    new StringBuilder().append("Username: ").append(friend.getName());
                    if (friend.getAlias() != null) {
                        string = " - Alias: " + friend.getAlias();
                    }
                    else {
                        string = "";
                    }
                    Printer.addMessage(sb.append(string).toString());
                    return;
                });
                break;
            }
        }
    }
}
