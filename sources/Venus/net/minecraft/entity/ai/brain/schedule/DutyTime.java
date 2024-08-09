/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.schedule;

public class DutyTime {
    private final int duration;
    private final float active;

    public DutyTime(int n, float f) {
        this.duration = n;
        this.active = f;
    }

    public int getDuration() {
        return this.duration;
    }

    public float getActive() {
        return this.active;
    }
}

