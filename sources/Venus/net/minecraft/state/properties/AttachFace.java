/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state.properties;

import net.minecraft.util.IStringSerializable;

public enum AttachFace implements IStringSerializable
{
    FLOOR("floor"),
    WALL("wall"),
    CEILING("ceiling");

    private final String name;

    private AttachFace(String string2) {
        this.name = string2;
    }

    @Override
    public String getString() {
        return this.name;
    }
}

