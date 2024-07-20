/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

public class VillagerData {
    private final int type;
    private final int profession;
    private final int level;

    public VillagerData(int type2, int profession, int level) {
        this.type = type2;
        this.profession = profession;
        this.level = level;
    }

    public int type() {
        return this.type;
    }

    public int profession() {
        return this.profession;
    }

    public int level() {
        return this.level;
    }

    @Deprecated
    public int getType() {
        return this.type;
    }

    @Deprecated
    public int getProfession() {
        return this.profession;
    }

    @Deprecated
    public int getLevel() {
        return this.level;
    }
}

