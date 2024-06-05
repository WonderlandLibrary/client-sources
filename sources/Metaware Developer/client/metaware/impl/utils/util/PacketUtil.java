package client.metaware.impl.utils.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class PacketUtil {
    private static final int MAX_THREADS = 75;
    static Minecraft mc = Minecraft.getMinecraft();
    private static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_THREADS);

    public static Future<Packet<?>> packetDelayed(Packet<?> packet, long delay) {
        return executor.submit(() -> {
            Thread.sleep(delay);
            Minecraft.getMinecraft().getNetHandler().addToSendQueueNoEvent(packet);
            return packet;
        });
    }

    public static void packetNoEvent(Packet<?> packet) {
        mc.getNetHandler().addToSendQueueNoEvent(packet);
    }

    public static void packet(Packet<?> packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void sendC04(double x, double y, double z, boolean ground, boolean silent) {
        if (silent) {
            packetNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
        } else {
            packet(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
        }
    }
}