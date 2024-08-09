/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.optifine.util.NumUtils;

public class SmoothFloat {
    private float valueLast;
    private float timeFadeUpSec;
    private float timeFadeDownSec;
    private long timeLastMs;

    public SmoothFloat(float f, float f2) {
        this(f, f2, f2);
    }

    public SmoothFloat(float f, float f2, float f3) {
        this.valueLast = f;
        this.timeFadeUpSec = f2;
        this.timeFadeDownSec = f3;
        this.timeLastMs = System.currentTimeMillis();
    }

    public float getValueLast() {
        return this.valueLast;
    }

    public float getTimeFadeUpSec() {
        return this.timeFadeUpSec;
    }

    public float getTimeFadeDownSec() {
        return this.timeFadeDownSec;
    }

    public long getTimeLastMs() {
        return this.timeLastMs;
    }

    public float getSmoothValue(float f, float f2, float f3) {
        this.timeFadeUpSec = f2;
        this.timeFadeDownSec = f3;
        return this.getSmoothValue(f);
    }

    public float getSmoothValue(float f) {
        float f2;
        long l = System.currentTimeMillis();
        float f3 = this.valueLast;
        long l2 = this.timeLastMs;
        float f4 = (float)(l - l2) / 1000.0f;
        float f5 = f >= f3 ? this.timeFadeUpSec : this.timeFadeDownSec;
        this.valueLast = f2 = SmoothFloat.getSmoothValue(f3, f, f4, f5);
        this.timeLastMs = l;
        return f2;
    }

    public static float getSmoothValue(float f, float f2, float f3, float f4) {
        float f5;
        if (f3 <= 0.0f) {
            return f;
        }
        float f6 = f2 - f;
        if (f4 > 0.0f && f3 < f4 && Math.abs(f6) > 1.0E-6f) {
            float f7 = f4 / f3;
            float f8 = 4.61f;
            float f9 = 0.13f;
            float f10 = 10.0f;
            float f11 = f8 - 1.0f / (f9 + f7 / f10);
            float f12 = f3 / f4 * f11;
            f12 = NumUtils.limit(f12, 0.0f, 1.0f);
            f5 = f + f6 * f12;
        } else {
            f5 = f2;
        }
        return f5;
    }
}

