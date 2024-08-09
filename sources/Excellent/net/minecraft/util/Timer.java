package net.minecraft.util;

import dev.excellent.Excellent;
import dev.excellent.api.event.impl.other.TimerEvent;
import lombok.Getter;
import lombok.Setter;

public class Timer {
    public float renderPartialTicks;
    public float elapsedPartialTicks;
    private long lastSyncSysClock;
    private final float tickLength;
    @Getter
    @Setter
    private float speed = 1F;

    public void resetSpeed() {
        setSpeed(1F);
    }

    public Timer(float ticks, long lastSyncSysClock) {
        this.tickLength = 1000.0F / ticks;
        this.lastSyncSysClock = lastSyncSysClock;
    }

    public int getPartialTicks(long gameTime) {
        final TimerEvent event = new TimerEvent(gameTime);
        Excellent.getInst().getEventBus().handle(event);

        this.elapsedPartialTicks = ((float) (gameTime - this.lastSyncSysClock) / this.tickLength) * speed;
        this.lastSyncSysClock = gameTime;
        this.renderPartialTicks += this.elapsedPartialTicks;
        int i = (int) this.renderPartialTicks;
        this.renderPartialTicks -= (float) i;
        return i;
    }
}
