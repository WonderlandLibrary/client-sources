package com.polarware.command.impl;

import com.polarware.Client;
import com.polarware.command.Command;
import com.polarware.util.chat.ChatUtil;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Alan
 * @since 10/19/2022
 */
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
            Boolean success = false;
            for (EntityPlayer entityPlayer: mc.theWorld.playerEntities) {
                if (!entityPlayer.getCommandSenderName().equalsIgnoreCase(target)) {
                    continue;
                }
                switch (action) {
                    case "add":
                        Client.INSTANCE.getBotComponent().add(entityPlayer);
                        ChatUtil.display(String.format("Added %s to friends list", target));
                        success = true;
                        break;

                    case "remove":
                        Client.INSTANCE.getBotComponent().remove(entityPlayer);
                        ChatUtil.display(String.format("Removed %s from friends list", target));
                        success = true;
                        break;
                }
                break;
            }
            if(!success) {
                ChatUtil.display("That user could not be found.");
            }
        }
    }
}