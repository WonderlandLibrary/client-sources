/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.ITextComponent;

public interface IDataAccessor {
    public void mergeData(CompoundNBT var1) throws CommandSyntaxException;

    public CompoundNBT getData() throws CommandSyntaxException;

    public ITextComponent getModifiedMessage();

    public ITextComponent getQueryMessage(INBT var1);

    public ITextComponent getGetMessage(NBTPathArgument.NBTPath var1, double var2, int var4);
}

