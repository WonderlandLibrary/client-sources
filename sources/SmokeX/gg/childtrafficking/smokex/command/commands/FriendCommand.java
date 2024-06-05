// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "Friend", description = "add friends", usage = ".f add [ign] | .f remove [ign] | .f clear | .e list", aliases = { "f" })
public final class FriendCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        if (args.length < 1) {
            this.printUsage();
        }
        else {
            final String lowerCase = args[0].toLowerCase();
            switch (lowerCase) {
                case "add": {
                    ChatUtils.addChatMessage("Added friends " + args[1]);
                    SmokeXClient.getInstance().getPlayerManager().addFriend(args[1]);
                    break;
                }
                case "remove": {
                    ChatUtils.addChatMessage("Removed friends " + args[1]);
                    SmokeXClient.getInstance().getPlayerManager().removeFriend(args[1]);
                    break;
                }
                case "clear": {
                    ChatUtils.addChatMessage("Cleared friends");
                    SmokeXClient.getInstance().getPlayerManager().clearFriends();
                    break;
                }
                case "list": {
                    if (!SmokeXClient.getInstance().getPlayerManager().getFriends().isEmpty()) {
                        SmokeXClient.getInstance().getPlayerManager().getFriends().forEach(friend -> ChatUtils.addChatMessage("- " + friend));
                        break;
                    }
                    ChatUtils.addChatMessage("The friends list is empty");
                    break;
                }
            }
        }
    }
}
