package com.alan.clients.command.impl;

import com.alan.clients.command.Command;
import com.alan.clients.component.impl.player.UserFriendAndTargetComponent;
import com.alan.clients.util.chat.ChatUtil;
import net.minecraft.entity.player.EntityPlayer;

public final class Target extends Command {

    public Target() {
        super("command.target.description", "target", "settarget");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length != 3) {
            error(".target <add/remove> <player>");
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
                        UserFriendAndTargetComponent.addTarget(entityPlayer.getCommandSenderName());
                        ChatUtil.display(String.format("Added %s to target list", target));
                        success = true;
                        break;

                    case "remove":
                        UserFriendAndTargetComponent.removeTarget(entityPlayer.getCommandSenderName());
                        ChatUtil.display(String.format("Removed %s from target list", target));
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