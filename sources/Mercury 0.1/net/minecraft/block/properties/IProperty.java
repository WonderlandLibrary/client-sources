/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.block.properties;

import java.util.Collection;

public interface IProperty {
    public String getName();

    public Collection getAllowedValues();

    public Class getValueClass();

    public String getName(Comparable var1);
}

