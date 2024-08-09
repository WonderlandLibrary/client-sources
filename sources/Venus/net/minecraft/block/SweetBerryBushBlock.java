/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SweetBerryBushBlock
extends BushBlock
implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    private static final VoxelShape BUSHLING_SHAPE = Block.makeCuboidShape(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
    private static final VoxelShape GROWING_SHAPE = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public SweetBerryBushBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(AGE, 0));
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(Items.SWEET_BERRIES);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        if (blockState.get(AGE) == 0) {
            return BUSHLING_SHAPE;
        }
        return blockState.get(AGE) < 3 ? GROWING_SHAPE : super.getShape(blockState, iBlockReader, blockPos, iSelectionContext);
    }

    @Override
    public boolean ticksRandomly(BlockState blockState) {
        return blockState.get(AGE) < 3;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        int n = blockState.get(AGE);
        if (n < 3 && random2.nextInt(5) == 0 && serverWorld.getLightSubtracted(blockPos.up(), 0) >= 9) {
            serverWorld.setBlockState(blockPos, (BlockState)blockState.with(AGE, n + 1), 1);
        }
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        if (entity2 instanceof LivingEntity && entity2.getType() != EntityType.FOX && entity2.getType() != EntityType.BEE) {
            entity2.setMotionMultiplier(blockState, new Vector3d(0.8f, 0.75, 0.8f));
            if (!(world.isRemote || blockState.get(AGE) <= 0 || entity2.lastTickPosX == entity2.getPosX() && entity2.lastTickPosZ == entity2.getPosZ())) {
                double d = Math.abs(entity2.getPosX() - entity2.lastTickPosX);
                double d2 = Math.abs(entity2.getPosZ() - entity2.lastTickPosZ);
                if (d >= (double)0.003f || d2 >= (double)0.003f) {
                    entity2.attackEntityFrom(DamageSource.SWEET_BERRY_BUSH, 1.0f);
                }
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        boolean bl;
        int n = blockState.get(AGE);
        boolean bl2 = bl = n == 3;
        if (!bl && playerEntity.getHeldItem(hand).getItem() == Items.BONE_MEAL) {
            return ActionResultType.PASS;
        }
        if (n > 1) {
            int n2 = 1 + world.rand.nextInt(2);
            SweetBerryBushBlock.spawnAsEntity(world, blockPos, new ItemStack(Items.SWEET_BERRIES, n2 + (bl ? 1 : 0)));
            world.playSound(null, blockPos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0f, 0.8f + world.rand.nextFloat() * 0.4f);
            world.setBlockState(blockPos, (BlockState)blockState.with(AGE, 1), 1);
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return super.onBlockActivated(blockState, world, blockPos, playerEntity, hand, blockRayTraceResult);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return blockState.get(AGE) < 3;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        int n = Math.min(3, blockState.get(AGE) + 1);
        serverWorld.setBlockState(blockPos, (BlockState)blockState.with(AGE, n), 1);
    }
}

