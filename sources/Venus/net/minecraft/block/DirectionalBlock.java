/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;

public abstract class DirectionalBlock
extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    protected DirectionalBlock(AbstractBlock.Properties properties) {
        super(properties);
    }
}

