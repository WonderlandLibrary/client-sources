/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.connection.StorableObject;

public class WorldIdentifiers
implements StorableObject {
    public static final String OVERWORLD_DEFAULT = "minecraft:overworld";
    public static final String NETHER_DEFAULT = "minecraft:the_nether";
    public static final String END_DEFAULT = "minecraft:the_end";
    private final String overworld;
    private final String nether;
    private final String end;

    public WorldIdentifiers(String string) {
        this(string, NETHER_DEFAULT, END_DEFAULT);
    }

    public WorldIdentifiers(String string, String string2, String string3) {
        this.overworld = string;
        this.nether = string2;
        this.end = string3;
    }

    public String overworld() {
        return this.overworld;
    }

    public String nether() {
        return this.nether;
    }

    public String end() {
        return this.end;
    }
}

