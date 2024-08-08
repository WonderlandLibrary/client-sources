package lol.point.returnclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public interface MinecraftInstance {

    Minecraft mc = Minecraft.getMinecraft();

    default void sendPacket(Packet<? extends INetHandler> packet) {
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }

    default void sendPacketNoEvent(Packet<? extends INetHandler> packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

}
