/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils;

import java.util.Arrays;
import ru.govno.client.event.EventManager;
import ru.govno.client.utils.Math.MathUtils;

public class TPSDetect {
    public static float[] tickRates = new float[20];
    public static int nextIndex = 0;
    public static long timeLastTimeUpdate;

    public TPSDetect() {
        nextIndex = 0;
        timeLastTimeUpdate = -1L;
        Arrays.fill(tickRates, 20.0f);
        EventManager.register(this);
    }

    public static float getTPSServer() {
        float numTicks = 0.0f;
        float sumTickRates = 0.0f;
        for (float tickRate : tickRates) {
            if (!(tickRate > 0.0f)) continue;
            sumTickRates += tickRate;
            numTicks += 1.0f;
        }
        return Float.isNaN(MathUtils.clamp(sumTickRates / numTicks, 0.5f, 20.0f)) ? 0.0f : MathUtils.clamp(sumTickRates / numTicks, 0.5f, 20.0f);
    }

    public static float getConpensationTPS(boolean doConpensation) {
        return doConpensation ? 1.0f / (TPSDetect.getTPSServer() / 20.0f) : 1.0f;
    }

    public static String getTpsString() {
        return String.format("%.2f", Float.valueOf(TPSDetect.getTPSServer()));
    }

    public static void onTimeUpdate() {
        if (timeLastTimeUpdate != -1L) {
            float timeElapsed = (float)(System.currentTimeMillis() - timeLastTimeUpdate) / 1000.0f;
            TPSDetect.tickRates[TPSDetect.nextIndex % TPSDetect.tickRates.length] = MathUtils.clamp(20.0f / timeElapsed, 0.5f, 20.0f);
            ++nextIndex;
        }
        timeLastTimeUpdate = System.currentTimeMillis();
    }
}

