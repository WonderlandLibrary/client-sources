package fr.dog.util.packet;

import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

@UtilityClass
public class PacketUtil {
    public void sendPacket(Packet packet){
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
    }
    public void sendPacketNoEvent(Packet packet){
        Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
    }

}
