/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.border;

public enum BorderStatus {
    GROWING(4259712),
    SHRINKING(0xFF3030),
    STATIONARY(2138367);

    private final int color;

    private BorderStatus(int n2) {
        this.color = n2;
    }

    public int getColor() {
        return this.color;
    }
}

