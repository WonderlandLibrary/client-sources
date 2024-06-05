package me.vaziak.sensation.client.api.command.impl;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.command.Command;
import me.vaziak.sensation.utils.client.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class VClipCommand extends Command {
    public VClipCommand() {
        super("vclip", "Teleports you up/down a distance", "<distance>", new String[]{});
    }

    @Override
    public boolean onCommand(String[] args) {
        if (args.length == 2) {
            double distance = Double.parseDouble(args[1]);

            Minecraft.getMinecraft().thePlayer.setPositionAndUpdate(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + distance, Minecraft.getMinecraft().thePlayer.posZ);
            ChatUtils.log("You have been teleported " + (distance > 0 ? "up" : "down") + " " + distance);
        } else {
            return false;
        }

        return true;
    }
}
