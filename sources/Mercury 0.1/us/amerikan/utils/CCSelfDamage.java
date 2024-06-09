/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class CCSelfDamage {
    Minecraft mc = Minecraft.getMinecraft();

    public void CCDamage(double damage) {
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer != null) {
            for (int i2 = 0; i2 < 32; ++i2) {
                double damage1 = 0.1;
                double lol = damage1 * damage;
                Minecraft.getMinecraft();
                Minecraft.getMinecraft();
                Minecraft.getMinecraft();
                this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + (0.0 + lol), Minecraft.thePlayer.posZ, false));
                Minecraft.getMinecraft();
                Minecraft.getMinecraft();
                Minecraft.getMinecraft();
                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - (0.0 + lol), Minecraft.thePlayer.posZ, false));
            }
            this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
        }
    }
}

