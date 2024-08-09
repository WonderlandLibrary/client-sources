/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state.properties;

import net.minecraft.util.IStringSerializable;

public enum RedstoneSide implements IStringSerializable
{
    UP("up"),
    SIDE("side"),
    NONE("none");

    private final String name;

    private RedstoneSide(String string2) {
        this.name = string2;
    }

    public String toString() {
        return this.getString();
    }

    @Override
    public String getString() {
        return this.name;
    }

    public boolean func_235921_b_() {
        return this != NONE;
    }
}

