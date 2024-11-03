package dev.star.utils.time;

import store.intent.intentguard.annotation.Exclude;
import store.intent.intentguard.annotation.Strategy;

public class TimerUtil {
    private long lastTime;

    public long lastMS = System.currentTimeMillis();

    @Exclude(Strategy.NAME_REMAPPING)
    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    @Exclude(Strategy.NAME_REMAPPING)
    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - lastMS > time) {
            if (reset) reset();
            return true;
        }

        return false;
    }

    public float getValueFloat(float begin, float end, int type) {
        if (this.lastMS == end) {
            return this.lastMS;
        } else {
            float t = (float) (System.currentTimeMillis() - this.lastTime) ;
            switch (type) {
                case 1:
                    t = t < 0.5F ? 4.0F * t * t * t : (t - 1.0F) * (2.0F * t - 2.0F) * (2.0F * t - 2.0F) + 1.0F;
                    break;
                case 2:
                    t = (float) (1.0D - Math.pow((double) (1.0F - t), 5.0D));
                    break;
                case 3:
                    t = this.bounce(t);
            }

            float value = begin + t * (end - begin);
            if (end < value) {
                value = end;
            }

            if (value == end) {
                this.lastMS = (long) value;
            }

            return value;
        }
    }

    private float bounce(float t) {
        float i = 0.0F;
        double i2 = 7.5625D;
        double i3 = 2.75D;
        if ((double) t < 1.0D / i3) {
            i = (float) (i2 * (double) t * (double) t);
        } else if ((double) t < 2.0D / i3) {
            i = (float) (i2 * (double) (t = (float) ((double) t - 1.5D / i3)) * (double) t + 0.75D);
        } else if ((double) t < 2.5D / i3) {
            i = (float) (i2 * (double) (t = (float) ((double) t - 2.25D / i3)) * (double) t + 0.9375D);
        } else {
            i = (float) (i2 * (double) (t = (float) ((double) t - 2.625D / i3)) * (double) t + 0.984375D);
        }

        return i;
    }



    public void start() {
        this.lastMS = (long) 0.0F;
        this.lastTime = System.currentTimeMillis();
    }

    @Exclude(Strategy.NAME_REMAPPING)
    public boolean hasTimeElapsed(long time) {
        return System.currentTimeMillis() - lastMS > time;
    }

    @Exclude(Strategy.NAME_REMAPPING)
    public boolean hasTimeElapsed(double time) {
        return hasTimeElapsed((long) time);
    }

    @Exclude(Strategy.NAME_REMAPPING)
    public long getTime() {
        return System.currentTimeMillis() - lastMS;
    }

    public void setTime(long time) {
        lastMS = time;
    }

}
