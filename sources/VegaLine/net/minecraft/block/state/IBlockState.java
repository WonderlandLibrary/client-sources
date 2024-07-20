/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block.state;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockBehaviors;
import net.minecraft.block.state.IBlockProperties;

public interface IBlockState
extends IBlockBehaviors,
IBlockProperties {
    public Collection<IProperty<?>> getPropertyNames();

    public <T extends Comparable<T>> T getValue(IProperty<T> var1);

    public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> var1, V var2);

    public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> var1);

    public ImmutableMap<IProperty<?>, Comparable<?>> getProperties();

    public Block getBlock();
}

