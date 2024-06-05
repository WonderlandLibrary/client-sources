package Focus.Beta.UTILS.world;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PacketUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void sendPacket(Packet packet){
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void sendPacketPlayer(Packet packet){
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }

    public static void sendPacketPlayerNoEvent(Packet packet){
        mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(packet);
    }

    public static void sendPacketNoEvent(Packet packet){
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
    }
    public static void sendPacketNoEventSilent(Packet packet){
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

    public static void sendPacketSilent(final Packet packet) {
        PacketUtil.mc.thePlayer.sendQueue.addToSendQueueSilent(packet);
    }

    public static void sendC04(final double x, final double y, final double z, final boolean ground, final boolean silent) {
        if (silent) {
            sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
        }
        else {
            sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
        }
    }


}
