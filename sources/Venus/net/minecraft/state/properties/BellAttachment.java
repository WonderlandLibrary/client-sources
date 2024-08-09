/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state.properties;

import net.minecraft.util.IStringSerializable;

public enum BellAttachment implements IStringSerializable
{
    FLOOR("floor"),
    CEILING("ceiling"),
    SINGLE_WALL("single_wall"),
    DOUBLE_WALL("double_wall");

    private final String name;

    private BellAttachment(String string2) {
        this.name = string2;
    }

    @Override
    public String getString() {
        return this.name;
    }
}

