package zamorozka.ui;

import com.ibm.icu.text.DecimalFormat;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public class TickRate {
    public static float TPS;
    public static TickRate INSTANCE;
    public static long lastUpdate;
    public static float[] tpsCounts;
    public static DecimalFormat format;

    static {
        TickRate.TPS = 20.0f;
        TickRate.lastUpdate = -1L;
        TickRate.tpsCounts = new float[10];
        TickRate.format = new DecimalFormat("##.0#");
    }

    private final float[] tickRates;

    public TickRate() {
        this.tickRates = new float[20];
    }

    public static void update(Packet packet) {
        if (!(packet instanceof SPacketTimeUpdate)) {
            return;
        }
        final long currentTime = System.currentTimeMillis();
        if (TickRate.lastUpdate == -1L) {
            TickRate.lastUpdate = currentTime;
            return;
        }
        final long timeDiff = currentTime - TickRate.lastUpdate;
        float tickTime = (float) (timeDiff / 20L);
        if (tickTime == 0.0f) {
            tickTime = 50.0f;
        }
        float tps = 1000.0f / tickTime;
        if (tps > 20.0f) {
            tps = 20.0f;
        }
        for (int i = TickRate.tpsCounts.length - 1; i > 0; --i) {
            TickRate.tpsCounts[i] = TickRate.tpsCounts[i - 1];
        }
        TickRate.tpsCounts[0] = tps;
        double total = 0.0;
        for (final float f : TickRate.tpsCounts) {
            total += f;
        }
        total /= TickRate.tpsCounts.length;
        if (total > 20.0) {
            total = 20.0;
        }
        TickRate.TPS = Float.parseFloat(TickRate.format.format(total));
        TickRate.lastUpdate = currentTime;
    }

    public static void reset() {
        Arrays.fill(TickRate.tpsCounts, 20.0f);
        TickRate.TPS = 20.0f;
    }

    public float getTickRate() {
        float numTicks = 0.0f;
        float sumTickRates = 0.0f;
        for (final float tickRate : this.tickRates) {
            if (tickRate > 0.0f) {
                sumTickRates += tickRate;
                ++numTicks;
            }
        }
        return MathHelper.clamp(sumTickRates / numTicks, 0.0f, 20.0f);
    }
}

