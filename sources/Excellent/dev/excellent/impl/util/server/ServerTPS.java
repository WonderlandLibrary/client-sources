package dev.excellent.impl.util.server;

import dev.excellent.Excellent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import lombok.Getter;
import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraft.util.math.MathHelper;

@Getter
public class ServerTPS {
    private float TPS = 20;
    private float adjustTicks = 0;

    private long timestamp;

    public ServerTPS() {
        Excellent.getInst().getEventBus().register(this);
    }


    protected void update() {
        long delay = System.nanoTime() - timestamp;

        float maxTPS = 20;
        float rawTPS = maxTPS * (1e9f / delay);

        float boundedTPS = MathHelper.clamp(rawTPS, 0, maxTPS);

        TPS = (float) round(boundedTPS);

        adjustTicks = boundedTPS - maxTPS;

        timestamp = System.nanoTime();
    }

    protected final Listener<PacketEvent> onPacket = event -> {
        if (event.getPacket() instanceof SUpdateTimePacket) update();
    };

    public double round(final double input) {
        return Math.round(input * 100.0) / 100.0;
    }
}