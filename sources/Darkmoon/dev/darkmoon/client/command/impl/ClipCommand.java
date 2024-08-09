package dev.darkmoon.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.command.Command;
import dev.darkmoon.client.command.CommandAbstract;
import dev.darkmoon.client.utility.math.MathUtility;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

import static dev.darkmoon.client.utility.Utility.mc;

@Command(name = "sclip", description = "clip for sunrise")
public class ClipCommand extends CommandAbstract {
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 1) {
            try {
                double x = -((double) MathHelper.sin(mc.player.rotationYaw * ((float) Math.PI / 180)) * 0.8);
                double z = (double) MathHelper.cos(mc.player.rotationYaw * ((float) Math.PI / 180)) * 0.8;
                for (int i = 0; i < 10; i++) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + x, mc.player.posY, mc.player.posZ + z, false));
                }
                mc.player.setPosition(mc.player.posX + x, mc.player.posY, mc.player.posZ + z);
            } catch (Exception ignored) {
            }
        } else {
            error();
        }
    }
    @Override
    public void error() {
        sendMessage(ChatFormatting.GRAY + "Ошибка в использовании" + ChatFormatting.WHITE + ":");
        sendMessage(ChatFormatting.WHITE + ".sclip " + ChatFormatting.GRAY + "<"
                + "(число вводить не нужно)" + ChatFormatting.GRAY + ">");
    }
}
