/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum WallHeight implements IStringSerializable
{
    NONE("none"),
    LOW("low"),
    TALL("tall");

    private final String heightName;

    private WallHeight(String string2) {
        this.heightName = string2;
    }

    public String toString() {
        return this.getString();
    }

    @Override
    public String getString() {
        return this.heightName;
    }
}

