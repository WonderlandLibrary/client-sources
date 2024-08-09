package wtf.resolute.utiled.client;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.EventPacket;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public class ServerTPS {
    protected final float[] ticks = new float[20];
    protected int index;
    protected long lastPacketTime;

    public ServerTPS() {
        this.index = 0;
        this.lastPacketTime = -1L;
        Arrays.fill(ticks, 0.0F);
        ResoluteInfo.getInstance().getEventBus().register(this);
    }

    public float getTPS() {
        float numTicks = 0.0F;
        float sumTickRates = 0.0F;
        for (float tickRate : ticks) {
            if (tickRate > 0.0F) {
                sumTickRates += tickRate;
                numTicks++;
            }
        }
        return MathHelper.clamp(0.0F, 20.0F, sumTickRates / numTicks);
    }

    private void update() {
        if (this.lastPacketTime != -1L) {
            float timeElapsed = (float) (System.currentTimeMillis() - this.lastPacketTime) / 1000.0F;
            ticks[this.index % ticks.length] = MathHelper.clamp(0.0F, 20.0F, 20.0F / timeElapsed);
            this.index++;
        }
        this.lastPacketTime = System.currentTimeMillis();
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        update();
    }
}
