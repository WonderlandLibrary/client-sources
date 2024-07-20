/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import ru.govno.client.Client;
import ru.govno.client.utils.Command.Command;

public class Motion
extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public Motion() {
        super("Motion", new String[]{"vmotion", "hmotion", "vm", "hm"});
    }

    @Override
    public void onCommand(String[] args) {
        try {
            if (args[0].equalsIgnoreCase("vmotion") || args[0].equalsIgnoreCase("vm")) {
                Client.msg("\u00a7e\u00a7lMotion:\u00a7r \u00a77\u0412\u044b \u0441\u0434\u0432\u0438\u043d\u0443\u0442\u044b \u043d\u0430 " + (Double.valueOf(args[1]) > 0.0 ? Double.valueOf(args[1]) : -Double.valueOf(args[1]).doubleValue()) + " \u043c\u043e\u0443\u0448\u043d " + (Double.valueOf(args[1]) > 0.0 ? "\u0432\u0432\u0435\u0440\u0445." : "\u0432\u043d\u0438\u0437."), false);
                Minecraft.player.motionY = Double.valueOf(args[1]);
                mc.getConnection().sendPacket(new CPacketPlayer(true));
            }
            if (args[0].equalsIgnoreCase("hmotion") || args[0].equalsIgnoreCase("hm")) {
                Client.msg("\u00a7e\u00a7lMotion:\u00a7r \u00a77\u0412\u044b \u0441\u0434\u0432\u0438\u043d\u0443\u0442\u044b \u043d\u0430 " + (Double.valueOf(args[1]) > 0.0 ? Double.valueOf(args[1]) : -Double.valueOf(args[1]).doubleValue()) + " \u043c\u043e\u0443\u0448\u043d " + (Double.valueOf(args[1]) > 0.0 ? "\u0432\u043f\u0435\u0440\u0451\u0434." : "\u043d\u0430\u0437\u0430\u0434."), false);
                float f = Minecraft.player.rotationYaw * ((float)Math.PI / 180);
                double speed = Double.valueOf(args[1]);
                double x = -((double)MathHelper.sin(f) * speed);
                double z = (double)MathHelper.cos(f) * speed;
                Minecraft.player.motionX = x;
                Minecraft.player.motionZ = z;
            }
        } catch (Exception formatException) {
            Client.msg("\u00a7e\u00a7lMotion:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a7e\u00a7lMotion:\u00a7r \u00a77vmotion: vmotion/vm [\u00a7ly+\u00a7r\u00a77]", false);
            Client.msg("\u00a7e\u00a7lMotion:\u00a7r \u00a77hmotion: hmotion/hm [\u00a7lh+\u00a7r\u00a77]", false);
            formatException.printStackTrace();
        }
    }
}

