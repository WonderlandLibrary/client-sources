/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class PotatoBlock
extends CropsBlock {
    private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 5.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)};

    public PotatoBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    protected IItemProvider getSeedsItem() {
        return Items.POTATO;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPES[blockState.get(this.getAgeProperty())];
    }
}

