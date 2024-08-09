/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.legacy.bossbar;

public enum BossColor {
    PINK(0),
    BLUE(1),
    RED(2),
    GREEN(3),
    YELLOW(4),
    PURPLE(5),
    WHITE(6);

    private final int id;

    private BossColor(int n2) {
        this.id = n2;
    }

    public int getId() {
        return this.id;
    }
}

