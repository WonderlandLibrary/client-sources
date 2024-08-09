/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TargetBlock
extends Block {
    private static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;

    public TargetBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(POWER, 0));
    }

    @Override
    public void onProjectileCollision(World world, BlockState blockState, BlockRayTraceResult blockRayTraceResult, ProjectileEntity projectileEntity) {
        int n = TargetBlock.getPowerFromHitVec(world, blockState, blockRayTraceResult, projectileEntity);
        Entity entity2 = projectileEntity.func_234616_v_();
        if (entity2 instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity2;
            serverPlayerEntity.addStat(Stats.field_232863_aD_);
            CriteriaTriggers.TARGET_HIT.test(serverPlayerEntity, projectileEntity, blockRayTraceResult.getHitVec(), n);
        }
    }

    private static int getPowerFromHitVec(IWorld iWorld, BlockState blockState, BlockRayTraceResult blockRayTraceResult, Entity entity2) {
        int n;
        int n2 = TargetBlock.getPowerFromHitVec(blockRayTraceResult, blockRayTraceResult.getHitVec());
        int n3 = n = entity2 instanceof AbstractArrowEntity ? 20 : 8;
        if (!iWorld.getPendingBlockTicks().isTickScheduled(blockRayTraceResult.getPos(), blockState.getBlock())) {
            TargetBlock.powerTarget(iWorld, blockState, n2, blockRayTraceResult.getPos(), n);
        }
        return n2;
    }

    private static int getPowerFromHitVec(BlockRayTraceResult blockRayTraceResult, Vector3d vector3d) {
        Direction direction = blockRayTraceResult.getFace();
        double d = Math.abs(MathHelper.frac(vector3d.x) - 0.5);
        double d2 = Math.abs(MathHelper.frac(vector3d.y) - 0.5);
        double d3 = Math.abs(MathHelper.frac(vector3d.z) - 0.5);
        Direction.Axis axis = direction.getAxis();
        double d4 = axis == Direction.Axis.Y ? Math.max(d, d3) : (axis == Direction.Axis.Z ? Math.max(d, d2) : Math.max(d2, d3));
        return Math.max(1, MathHelper.ceil(15.0 * MathHelper.clamp((0.5 - d4) / 0.5, 0.0, 1.0)));
    }

    private static void powerTarget(IWorld iWorld, BlockState blockState, int n, BlockPos blockPos, int n2) {
        iWorld.setBlockState(blockPos, (BlockState)blockState.with(POWER, n), 3);
        iWorld.getPendingBlockTicks().scheduleTick(blockPos, blockState.getBlock(), n2);
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (blockState.get(POWER) != 0) {
            serverWorld.setBlockState(blockPos, (BlockState)blockState.with(POWER, 0), 0);
        }
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.get(POWER);
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!(world.isRemote() || blockState.isIn(blockState2.getBlock()) || blockState.get(POWER) <= 0 || world.getPendingBlockTicks().isTickScheduled(blockPos, this))) {
            world.setBlockState(blockPos, (BlockState)blockState.with(POWER, 0), 1);
        }
    }
}

