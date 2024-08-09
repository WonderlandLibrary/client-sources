/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class NetherrackBlock
extends Block
implements IGrowable {
    public NetherrackBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        if (!iBlockReader.getBlockState(blockPos.up()).propagatesSkylightDown(iBlockReader, blockPos)) {
            return true;
        }
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.add(-1, -1, -1), blockPos.add(1, 1, 1))) {
            if (!iBlockReader.getBlockState(blockPos2).isIn(BlockTags.NYLIUM)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        boolean bl = false;
        boolean bl2 = false;
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.add(-1, -1, -1), blockPos.add(1, 1, 1))) {
            BlockState blockState2 = serverWorld.getBlockState(blockPos2);
            if (blockState2.isIn(Blocks.WARPED_NYLIUM)) {
                bl2 = true;
            }
            if (blockState2.isIn(Blocks.CRIMSON_NYLIUM)) {
                bl = true;
            }
            if (!bl2 || !bl) continue;
            break;
        }
        if (bl2 && bl) {
            serverWorld.setBlockState(blockPos, random2.nextBoolean() ? Blocks.WARPED_NYLIUM.getDefaultState() : Blocks.CRIMSON_NYLIUM.getDefaultState(), 0);
        } else if (bl2) {
            serverWorld.setBlockState(blockPos, Blocks.WARPED_NYLIUM.getDefaultState(), 0);
        } else if (bl) {
            serverWorld.setBlockState(blockPos, Blocks.CRIMSON_NYLIUM.getDefaultState(), 0);
        }
    }
}

