/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class SoulFireBlock
extends AbstractFireBlock {
    public SoulFireBlock(AbstractBlock.Properties properties) {
        super(properties, 2.0f);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return this.isValidPosition(blockState, iWorld, blockPos) ? this.getDefaultState() : Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return SoulFireBlock.shouldLightSoulFire(iWorldReader.getBlockState(blockPos.down()).getBlock());
    }

    public static boolean shouldLightSoulFire(Block block) {
        return block.isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS);
    }

    @Override
    protected boolean canBurn(BlockState blockState) {
        return false;
    }
}

