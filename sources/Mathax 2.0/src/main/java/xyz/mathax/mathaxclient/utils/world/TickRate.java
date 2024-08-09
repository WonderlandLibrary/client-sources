package xyz.mathax.mathaxclient.utils.world;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.game.GameJoinedEvent;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.utils.Utils;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

import java.util.Arrays;

public class TickRate {
    public static TickRate INSTANCE = new TickRate();

    private final float[] tickRates = new float[20];

    private long timeLastTimeUpdate = -1;
    private long timeGameJoined;

    private int nextIndex = 0;

    private TickRate() {
        MatHax.EVENT_BUS.subscribe(this);
    }

    @EventHandler
    private void onReceivePacket(PacketEvent.Receive event) {
        if (event.packet instanceof WorldTimeUpdateS2CPacket) {
            long now = Utils.getCurrentTimeMillis();
            float timeElapsed = (float) (now - timeLastTimeUpdate) / 1000.0F;
            tickRates[nextIndex] = Utils.clamp(20.0f / timeElapsed, 0.0f, 20.0f);
            nextIndex = (nextIndex + 1) % tickRates.length;
            timeLastTimeUpdate = now;
        }
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent event) {
        Arrays.fill(tickRates, 0);

        nextIndex = 0;

        timeGameJoined = timeLastTimeUpdate = Utils.getCurrentTimeMillis();
    }

    public float getTickRate() {
        if (!Utils.canUpdate()) {
            return 0;
        }

        if (Utils.getCurrentTimeMillis() - timeGameJoined < 4000) {
            return 20;
        }

        int numTicks = 0;
        float sumTickRates = 0.0f;
        for (float tickRate : tickRates) {
            if (tickRate > 0) {
                sumTickRates += tickRate;
                numTicks++;
            }
        }

        return sumTickRates / numTicks;
    }

    public float getTimeSinceLastTick() {
        long now = Utils.getCurrentTimeMillis();

        if (now - timeGameJoined < 4000) {
            return 0;
        }

        return (now - timeLastTimeUpdate) / 1000f;
    }
}
