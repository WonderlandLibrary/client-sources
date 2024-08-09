/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.legacy.bossbar;

public enum BossFlag {
    DARKEN_SKY(1),
    PLAY_BOSS_MUSIC(2),
    CREATE_FOG(4);

    private final int id;

    private BossFlag(int n2) {
        this.id = n2;
    }

    public int getId() {
        return this.id;
    }
}

