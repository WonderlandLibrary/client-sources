package club.strifeclient.util.networking;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.Client;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.util.misc.MinecraftUtil;
import net.minecraft.network.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PacketUtil extends MinecraftUtil {

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(75);
    private static final List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event -> scheduledFutures.removeIf(Future::isDone);

    public static void sendPacketNoEvent(Packet<?> packet) {
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
    }

    public static void sendPacket(Packet<?> packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

    public static void sendPacketTimes(Packet<?> packet, int timesToSend) {
        for (int i = 0; i < timesToSend; i++) {
            sendPacket(packet);
        }
    }

    public static void packetTimesNoEvent(Packet<?> packet, int timesToSend) {
        for (int i = 0; i < timesToSend; i++) {
            sendPacketNoEvent(packet);
        }
    }

    public static void sendPacketDelayed(Packet<?> packet, long delay) {
        scheduledFutures.add(scheduledExecutorService.schedule(() -> sendPacket(packet), delay, TimeUnit.MILLISECONDS));
    }

    public static void sendPacketDelayedNoEvent(Packet<?> packet, long delay) {
        scheduledFutures.add(scheduledExecutorService.schedule(() -> sendPacketNoEvent(packet), delay, TimeUnit.MILLISECONDS));
    }
}
