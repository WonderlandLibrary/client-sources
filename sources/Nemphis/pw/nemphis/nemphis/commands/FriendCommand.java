/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.commands;

import java.util.List;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.command.Command;
import pw.vertexcode.nemphis.friend.Friend;
import pw.vertexcode.nemphis.friend.FriendManager;

public class FriendCommand
extends Command {
    @Override
    public String getName() {
        return "friend";
    }

    @Override
    public String getDescription() {
        return "Add Friends.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 1) {
            this.sendMessage("Invalid Arguments.", true);
        }
        if (args.length == 2 && args[1].equalsIgnoreCase("clear")) {
            Nemphis.instance.friendManager.getFriends().clear();
        }
    }
}

