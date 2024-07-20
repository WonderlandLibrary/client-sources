/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IDataFixer;

public interface IDataWalker {
    public NBTTagCompound process(IDataFixer var1, NBTTagCompound var2, int var3);
}

