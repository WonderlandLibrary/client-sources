/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.bridge.game;

public interface PerformanceMetrics {
    public int getMinTime();

    public int getMaxTime();

    public int getAverageTime();

    public int getSampleCount();
}

