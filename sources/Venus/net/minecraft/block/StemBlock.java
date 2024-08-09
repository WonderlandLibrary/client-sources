/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.StemGrownBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class StemBlock
extends BushBlock
implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{Block.makeCuboidShape(7.0, 0.0, 7.0, 9.0, 2.0, 9.0), Block.makeCuboidShape(7.0, 0.0, 7.0, 9.0, 4.0, 9.0), Block.makeCuboidShape(7.0, 0.0, 7.0, 9.0, 6.0, 9.0), Block.makeCuboidShape(7.0, 0.0, 7.0, 9.0, 8.0, 9.0), Block.makeCuboidShape(7.0, 0.0, 7.0, 9.0, 10.0, 9.0), Block.makeCuboidShape(7.0, 0.0, 7.0, 9.0, 12.0, 9.0), Block.makeCuboidShape(7.0, 0.0, 7.0, 9.0, 14.0, 9.0), Block.makeCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)};
    private final StemGrownBlock crop;

    protected StemBlock(StemGrownBlock stemGrownBlock, AbstractBlock.Properties properties) {
        super(properties);
        this.crop = stemGrownBlock;
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPES[blockState.get(AGE)];
    }

    @Override
    protected boolean isValidGround(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.isIn(Blocks.FARMLAND);
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        float f;
        if (serverWorld.getLightSubtracted(blockPos, 0) >= 9 && random2.nextInt((int)(25.0f / (f = CropsBlock.getGrowthChance(this, serverWorld, blockPos))) + 1) == 0) {
            int n = blockState.get(AGE);
            if (n < 7) {
                blockState = (BlockState)blockState.with(AGE, n + 1);
                serverWorld.setBlockState(blockPos, blockState, 1);
            } else {
                Direction direction = Direction.Plane.HORIZONTAL.random(random2);
                BlockPos blockPos2 = blockPos.offset(direction);
                BlockState blockState2 = serverWorld.getBlockState(blockPos2.down());
                if (serverWorld.getBlockState(blockPos2).isAir() && (blockState2.isIn(Blocks.FARMLAND) || blockState2.isIn(Blocks.DIRT) || blockState2.isIn(Blocks.COARSE_DIRT) || blockState2.isIn(Blocks.PODZOL) || blockState2.isIn(Blocks.GRASS_BLOCK))) {
                    serverWorld.setBlockState(blockPos2, this.crop.getDefaultState());
                    serverWorld.setBlockState(blockPos, (BlockState)this.crop.getAttachedStem().getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, direction));
                }
            }
        }
    }

    @Nullable
    protected Item getSeedItem() {
        if (this.crop == Blocks.PUMPKIN) {
            return Items.PUMPKIN_SEEDS;
        }
        return this.crop == Blocks.MELON ? Items.MELON_SEEDS : null;
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        Item item = this.getSeedItem();
        return item == null ? ItemStack.EMPTY : new ItemStack(item);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return blockState.get(AGE) != 7;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        int n = Math.min(7, blockState.get(AGE) + MathHelper.nextInt(serverWorld.rand, 2, 5));
        BlockState blockState2 = (BlockState)blockState.with(AGE, n);
        serverWorld.setBlockState(blockPos, blockState2, 1);
        if (n == 7) {
            blockState2.randomTick(serverWorld, blockPos, serverWorld.rand);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public StemGrownBlock getCrop() {
        return this.crop;
    }
}

