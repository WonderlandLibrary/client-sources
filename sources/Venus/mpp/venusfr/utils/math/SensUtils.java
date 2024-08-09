/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.math;

import mpp.venusfr.utils.client.IMinecraft;

public final class SensUtils
implements IMinecraft {
    public static float getSensitivity(float f) {
        return SensUtils.getDeltaMouse(f) * SensUtils.getGCDValue();
    }

    public static float getGCDValue() {
        return (float)((double)SensUtils.getGCD() * 0.15);
    }

    public static float getGCD() {
        float f = (float)(SensUtils.mc.gameSettings.mouseSensitivity * 0.6 + 0.2);
        return f * f * f * 8.0f;
    }

    public static float getDeltaMouse(float f) {
        return Math.round(f / SensUtils.getGCDValue());
    }

    private SensUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

