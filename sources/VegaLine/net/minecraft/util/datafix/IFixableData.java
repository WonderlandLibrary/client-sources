/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix;

import net.minecraft.nbt.NBTTagCompound;

public interface IFixableData {
    public int getFixVersion();

    public NBTTagCompound fixTagCompound(NBTTagCompound var1);
}

