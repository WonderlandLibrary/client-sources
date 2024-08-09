/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.venusfr;
import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraft.util.math.MathHelper;

public class TPSCalc {
    private float TPS = 20.0f;
    private float adjustTicks = 0.0f;
    private long timestamp;

    public TPSCalc() {
        venusfr.getInstance().getEventBus().register(this);
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        if (eventPacket.getPacket() instanceof SUpdateTimePacket) {
            this.updateTPS();
        }
    }

    private void updateTPS() {
        long l = System.nanoTime() - this.timestamp;
        float f = 20.0f;
        float f2 = f * (1.0E9f / (float)l);
        float f3 = MathHelper.clamp(f2, 0.0f, f);
        this.TPS = (float)this.round(f3);
        this.adjustTicks = f3 - f;
        this.timestamp = System.nanoTime();
    }

    public double round(double d) {
        return (double)Math.round(d * 100.0) / 100.0;
    }

    public float getTPS() {
        return this.TPS;
    }

    public float getAdjustTicks() {
        return this.adjustTicks;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}

