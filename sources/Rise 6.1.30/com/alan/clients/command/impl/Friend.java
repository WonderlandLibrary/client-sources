package com.alan.clients.command.impl;

import com.alan.clients.command.Command;
import com.alan.clients.component.impl.player.UserFriendAndTargetComponent;
import com.alan.clients.util.chat.ChatUtil;
import net.minecraft.entity.player.EntityPlayer;

public final class Friend extends Command {

    public Friend() {
        super("command.friend.description", "friend", "setfriend", "f");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length != 3) {
            error(".f <add/remove> <player>");
        } else {
            String action = args[1].toLowerCase();
            String target = args[2];
            boolean success = false;
            for (EntityPlayer entityPlayer : mc.theWorld.playerEntities) {
                if (!entityPlayer.getCommandSenderName().equalsIgnoreCase(target)) {
                    continue;
                }
                switch (action) {
                    case "add":
                        UserFriendAndTargetComponent.addFriend(entityPlayer.getCommandSenderName());
                        ChatUtil.display(String.format("Added %s to friends list", target));
                        success = true;
                        break;

                    case "remove":
                        UserFriendAndTargetComponent.removeFriend(entityPlayer.getCommandSenderName());
                        ChatUtil.display(String.format("Removed %s from friends list", target));
                        success = true;
                        break;
                }
                break;
            }
            if (!success) {
                ChatUtil.display("That user could not be found.");
            }
        }
    }
}