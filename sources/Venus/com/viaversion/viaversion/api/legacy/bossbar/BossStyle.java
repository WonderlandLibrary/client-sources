/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.legacy.bossbar;

public enum BossStyle {
    SOLID(0),
    SEGMENTED_6(1),
    SEGMENTED_10(2),
    SEGMENTED_12(3),
    SEGMENTED_20(4);

    private final int id;

    private BossStyle(int n2) {
        this.id = n2;
    }

    public int getId() {
        return this.id;
    }
}

