/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state.properties;

import net.minecraft.util.IStringSerializable;

public enum ChestType implements IStringSerializable
{
    SINGLE("single", 0),
    LEFT("left", 2),
    RIGHT("right", 1);

    public static final ChestType[] VALUES;
    private final String name;
    private final int opposite;

    private ChestType(String string2, int n2) {
        this.name = string2;
        this.opposite = n2;
    }

    @Override
    public String getString() {
        return this.name;
    }

    public ChestType opposite() {
        return VALUES[this.opposite];
    }

    static {
        VALUES = ChestType.values();
    }
}

