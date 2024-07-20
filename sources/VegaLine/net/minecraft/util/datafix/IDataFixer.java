/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixType;

public interface IDataFixer {
    public NBTTagCompound process(IFixType var1, NBTTagCompound var2, int var3);
}

