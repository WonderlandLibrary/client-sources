/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SandBlock
extends FallingBlock {
    private final int dustColor;

    public SandBlock(int n, AbstractBlock.Properties properties) {
        super(properties);
        this.dustColor = n;
    }

    @Override
    public int getDustColor(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return this.dustColor;
    }
}

