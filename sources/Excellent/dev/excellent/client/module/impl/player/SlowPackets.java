package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.value.impl.NumberValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.IPacket;

import java.util.concurrent.ConcurrentLinkedQueue;

@ModuleInfo(name = "Slow Packets", description = "Задержка пакетов", category = Category.PLAYER)
public class SlowPackets extends Module {
    private final NumberValue delay = new NumberValue("Задержка", this, 1000, 100, 5000, 50);
    public static final ConcurrentLinkedQueue<TimedPacket> packets = new ConcurrentLinkedQueue<>();

    @Override
    protected void onEnable() {
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        for (TimedPacket p : packets) {
            mc.player.connection.getNetworkManager().sendPacketWithoutEvent(p.getPacket());
        }
        packets.clear();
    }

    private final Listener<WorldChangeEvent> onWorldChange = event -> toggle();
    private final Listener<WorldLoadEvent> onWorldLoad = event -> toggle();
    private final Listener<PacketEvent> onPacket = event -> {
        if (event.isSent() && mc.player != null && mc.world != null) {
            IPacket<?> packet = event.getPacket();
            packets.add(new TimedPacket(packet, System.currentTimeMillis()));
            event.cancel();
        }
    };

    private final Listener<MotionEvent> onMotion = event -> {
        for (TimedPacket timedPacket : packets) {
            if (System.currentTimeMillis() - timedPacket.getTime() >= delay.getValue().intValue()) {
                mc.player.connection.getNetworkManager().sendPacketWithoutEvent(timedPacket.getPacket());
                packets.remove(timedPacket);
            }
        }
    };

    @Getter
    @RequiredArgsConstructor
    public static class TimedPacket {
        private final IPacket<?> packet;
        private final long time;
    }
}
