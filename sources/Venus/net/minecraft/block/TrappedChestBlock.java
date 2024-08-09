/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;

public class TrappedChestBlock
extends ChestBlock {
    public TrappedChestBlock(AbstractBlock.Properties properties) {
        super(properties, TrappedChestBlock::lambda$new$0);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new TrappedChestTileEntity();
    }

    @Override
    protected Stat<ResourceLocation> getOpenStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return MathHelper.clamp(ChestTileEntity.getPlayersUsing(iBlockReader, blockPos), 0, 15);
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return direction == Direction.UP ? blockState.getWeakPower(iBlockReader, blockPos, direction) : 0;
    }

    private static TileEntityType lambda$new$0() {
        return TileEntityType.TRAPPED_CHEST;
    }
}

