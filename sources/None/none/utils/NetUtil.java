package none.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class NetUtil {
	private static Minecraft mc = Minecraft.getMinecraft();
	
    public static void sendPacketNoEvents(Packet packet) {
        mc.getConnection().getNetworkManager().sendPacketNoEvent(packet);
    }

    public static void sendPacket(Packet packet) {
        mc.thePlayer.connection.sendPacket(packet);
    }
}
