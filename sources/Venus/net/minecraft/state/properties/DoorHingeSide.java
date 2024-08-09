/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state.properties;

import net.minecraft.util.IStringSerializable;

public enum DoorHingeSide implements IStringSerializable
{
    LEFT,
    RIGHT;


    public String toString() {
        return this.getString();
    }

    @Override
    public String getString() {
        return this == LEFT ? "left" : "right";
    }
}

