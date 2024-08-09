/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.client;

import com.google.common.eventbus.Subscribe;
import java.util.Arrays;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.venusfr;
import net.minecraft.util.math.MathHelper;

public class ServerTPS {
    protected final float[] ticks = new float[20];
    protected int index = 0;
    protected long lastPacketTime = -1L;

    public ServerTPS() {
        Arrays.fill(this.ticks, 0.0f);
        venusfr.getInstance().getEventBus().register(this);
    }

    public float getTPS() {
        float f = 0.0f;
        float f2 = 0.0f;
        for (float f3 : this.ticks) {
            if (!(f3 > 0.0f)) continue;
            f2 += f3;
            f += 1.0f;
        }
        return MathHelper.clamp(0.0f, 20.0f, f2 / f);
    }

    private void update() {
        if (this.lastPacketTime != -1L) {
            float f = (float)(System.currentTimeMillis() - this.lastPacketTime) / 1000.0f;
            this.ticks[this.index % this.ticks.length] = MathHelper.clamp(0.0f, 20.0f, 20.0f / f);
            ++this.index;
        }
        this.lastPacketTime = System.currentTimeMillis();
    }

    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        this.update();
    }
}

