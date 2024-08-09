/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateHolder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CropsBlock
extends BushBlock
implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

    protected CropsBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(this.getAgeProperty(), 0));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE_BY_AGE[blockState.get(this.getAgeProperty())];
    }

    @Override
    protected boolean isValidGround(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.isIn(Blocks.FARMLAND);
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 0;
    }

    protected int getAge(BlockState blockState) {
        return blockState.get(this.getAgeProperty());
    }

    public BlockState withAge(int n) {
        return (BlockState)this.getDefaultState().with(this.getAgeProperty(), n);
    }

    public boolean isMaxAge(BlockState blockState) {
        return blockState.get(this.getAgeProperty()) >= this.getMaxAge();
    }

    @Override
    public boolean ticksRandomly(BlockState blockState) {
        return !this.isMaxAge(blockState);
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        float f;
        int n;
        if (serverWorld.getLightSubtracted(blockPos, 0) >= 9 && (n = this.getAge(blockState)) < this.getMaxAge() && random2.nextInt((int)(25.0f / (f = CropsBlock.getGrowthChance(this, serverWorld, blockPos))) + 1) == 0) {
            serverWorld.setBlockState(blockPos, this.withAge(n + 1), 1);
        }
    }

    public void grow(World world, BlockPos blockPos, BlockState blockState) {
        int n;
        int n2 = this.getAge(blockState) + this.getBonemealAgeIncrease(world);
        if (n2 > (n = this.getMaxAge())) {
            n2 = n;
        }
        world.setBlockState(blockPos, this.withAge(n2), 1);
    }

    protected int getBonemealAgeIncrease(World world) {
        return MathHelper.nextInt(world.rand, 2, 5);
    }

    protected static float getGrowthChance(Block block, IBlockReader iBlockReader, BlockPos blockPos) {
        boolean bl;
        Object object;
        float f = 1.0f;
        BlockPos blockPos2 = blockPos.down();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f2 = 0.0f;
                object = iBlockReader.getBlockState(blockPos2.add(i, 0, j));
                if (((AbstractBlock.AbstractBlockState)object).isIn(Blocks.FARMLAND)) {
                    f2 = 1.0f;
                    if (((StateHolder)object).get(FarmlandBlock.MOISTURE) > 0) {
                        f2 = 3.0f;
                    }
                }
                if (i != 0 || j != 0) {
                    f2 /= 4.0f;
                }
                f += f2;
            }
        }
        BlockPos blockPos3 = blockPos.north();
        BlockPos blockPos4 = blockPos.south();
        BlockPos blockPos5 = blockPos.west();
        object = blockPos.east();
        boolean bl2 = block == iBlockReader.getBlockState(blockPos5).getBlock() || block == iBlockReader.getBlockState((BlockPos)object).getBlock();
        boolean bl3 = bl = block == iBlockReader.getBlockState(blockPos3).getBlock() || block == iBlockReader.getBlockState(blockPos4).getBlock();
        if (bl2 && bl) {
            f /= 2.0f;
        } else {
            boolean bl4;
            boolean bl5 = bl4 = block == iBlockReader.getBlockState(blockPos5.north()).getBlock() || block == iBlockReader.getBlockState(((BlockPos)object).north()).getBlock() || block == iBlockReader.getBlockState(((BlockPos)object).south()).getBlock() || block == iBlockReader.getBlockState(blockPos5.south()).getBlock();
            if (bl4) {
                f /= 2.0f;
            }
        }
        return f;
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return (iWorldReader.getLightSubtracted(blockPos, 0) >= 8 || iWorldReader.canSeeSky(blockPos)) && super.isValidPosition(blockState, iWorldReader, blockPos);
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        if (entity2 instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
            world.destroyBlock(blockPos, true, entity2);
        }
        super.onEntityCollision(blockState, world, blockPos, entity2);
    }

    protected IItemProvider getSeedsItem() {
        return Items.WHEAT_SEEDS;
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(this.getSeedsItem());
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return !this.isMaxAge(blockState);
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        this.grow(serverWorld, blockPos, blockState);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}

