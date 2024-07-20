/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.client.Minecraft;
import ru.govno.client.module.modules.GameSyncTPS;

public class Timer {
    public float renderPartialTicks;
    public double speed = 1.0;
    public int elapsedTicks;
    public float field_194147_b;
    public float field_194148_c;
    private long lastSyncSysClock = Minecraft.getSystemTime();

    public Timer(float tps) {
    }

    public double getGameSpeed() {
        return this.speed * GameSyncTPS.getGameConpense(1.0, GameSyncTPS.instance.SyncPercent.fValue);
    }

    public void updateTimer() {
        long i = Minecraft.getSystemTime();
        this.field_194148_c = (float)(((double)i - (double)this.lastSyncSysClock) / (50.0 / this.getGameSpeed()));
        this.lastSyncSysClock = i;
        this.field_194147_b += this.field_194148_c;
        this.elapsedTicks = (int)this.field_194147_b;
        this.field_194147_b -= (float)this.elapsedTicks;
        this.renderPartialTicks = this.field_194147_b;
    }
}

