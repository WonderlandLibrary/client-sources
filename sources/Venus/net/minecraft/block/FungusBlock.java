/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.server.ServerWorld;

public class FungusBlock
extends BushBlock
implements IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);
    private final Supplier<ConfiguredFeature<HugeFungusConfig, ?>> fungusFeature;

    protected FungusBlock(AbstractBlock.Properties properties, Supplier<ConfiguredFeature<HugeFungusConfig, ?>> supplier) {
        super(properties);
        this.fungusFeature = supplier;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    protected boolean isValidGround(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.isIn(BlockTags.NYLIUM) || blockState.isIn(Blocks.MYCELIUM) || blockState.isIn(Blocks.SOUL_SOIL) || super.isValidGround(blockState, iBlockReader, blockPos);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        Block block = ((HugeFungusConfig)this.fungusFeature.get().config).field_236303_f_.getBlock();
        Block block2 = iBlockReader.getBlockState(blockPos.down()).getBlock();
        return block2 == block;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return (double)random2.nextFloat() < 0.4;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        this.fungusFeature.get().func_242765_a(serverWorld, serverWorld.getChunkProvider().getChunkGenerator(), random2, blockPos);
    }
}

