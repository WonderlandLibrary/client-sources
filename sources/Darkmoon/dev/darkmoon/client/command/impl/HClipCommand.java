package dev.darkmoon.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.command.Command;
import dev.darkmoon.client.command.CommandAbstract;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;

import static dev.darkmoon.client.utility.Utility.mc;

@Command(name = "hclip", description = "Позволяет телепортироваться по горизонтали")
public class HClipCommand extends CommandAbstract {

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length >= 2) {
            try {
                if (NumberUtils.isNumber(args[1])) {
                    float f = mc.player.rotationYaw * 0.017453292F;
                    double speed = Double.parseDouble(args[1]);
                    double x = -(MathHelper.sin(f) * speed);
                    double z = MathHelper.cos(f) * speed;
                    mc.player.setPositionAndUpdate(mc.player.posX + x, mc.player.posY, mc.player.posZ + z);
                } else {
                    sendMessage(ChatFormatting.GRAY + "Ошибка в использовании" + ChatFormatting.WHITE + ":");
                    sendMessage(ChatFormatting.RED + args[1] + ChatFormatting.GRAY + " не является числом!");
                }
            } catch (Exception ignored) {
            }
        } else {
            error();
        }
    }

    @Override
    public void error() {
        sendMessage(ChatFormatting.GRAY + "Ошибка в использовании" + ChatFormatting.WHITE + ":");
        sendMessage(ChatFormatting.WHITE + ".hclip " + ChatFormatting.GRAY + "<"
                + "value" + ChatFormatting.GRAY + ">");
    }
}
