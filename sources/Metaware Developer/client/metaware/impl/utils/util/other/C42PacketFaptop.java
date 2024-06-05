package client.metaware.impl.utils.util.other;

import com.sun.jmx.remote.internal.ArrayQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class C42PacketFaptop {
    private static final int MAX_THREADS = 75;
    private static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_THREADS);

    public static Future<Packet<?>> sendPacketDelayed(Packet<?> packet, long delay) {
        return executor.submit(() -> {
            Thread.sleep(delay);
            Minecraft.getMinecraft().getNetHandler().addToSendQueueNoEvent(packet);
            return packet;
        });
    }

    public static Future<Packet<?>> sendPacketDelayedWithList(Packet<?> packet, long delay, ArrayQueue<Packet<?>> packets, TimeHelper timer) {
        return executor.submit(() -> {
            Thread.sleep(delay);
            Minecraft.getMinecraft().getNetHandler().addToSendQueueNoEvent(packet);
            return packet;
        });
    }
}
