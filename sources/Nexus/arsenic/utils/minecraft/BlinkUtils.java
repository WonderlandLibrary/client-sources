package arsenic.utils.minecraft;

import arsenic.event.impl.EventPacket;
import arsenic.utils.java.UtilityClass;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.LinkedList;

public class BlinkUtils extends UtilityClass {
    private static final LinkedList<Packet<?>> packets = new LinkedList<>();

    public static void resetAll() {
        while (!packets.isEmpty()) {
            PacketUtil.send(packets.poll());
        }
    }

    public static void CancelAll(final EventPacket.OutGoing eventPacket) {
        if (mc.theWorld == null) {
            return;
        }
        if (eventPacket.getPacket() != null) {
            packets.add(eventPacket.getPacket());
            eventPacket.cancel();
        }
    }

    public static void CancelOther(final EventPacket.OutGoing eventPacket) {
        if (mc.theWorld == null) {
            return;
        }
        if (eventPacket.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition
                || eventPacket.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook
                || eventPacket.getPacket() instanceof C08PacketPlayerBlockPlacement
                || eventPacket.getPacket() instanceof C0APacketAnimation
                || eventPacket.getPacket() instanceof C08PacketPlayerBlockPlacement
                || eventPacket.getPacket() instanceof C02PacketUseEntity
                || eventPacket.getPacket() instanceof C0FPacketConfirmTransaction) {
            packets.add(eventPacket.getPacket());
            eventPacket.setCancelled(true);
        }
    }

    public static void CancelMove(final EventPacket.OutGoing eventPacket) {
        if (mc.theWorld == null) {
            return;
        }
        if (eventPacket.getPacket() instanceof C03PacketPlayer) {
            eventPacket.cancel();
        }
    }
}
