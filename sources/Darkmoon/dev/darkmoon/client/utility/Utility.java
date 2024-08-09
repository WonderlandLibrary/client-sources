package dev.darkmoon.client.utility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;

public interface Utility {
    Minecraft mc = Minecraft.getMinecraft();
    public static void sendPacket(Packet<?> packetIn) {
        getPlayer().connection.sendPacket(packetIn);
    }
    public static EntityPlayerSP getPlayer() {
        return mc.player;
    }

}
