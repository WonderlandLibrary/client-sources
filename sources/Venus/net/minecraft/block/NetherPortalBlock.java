/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PortalSize;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class NetherPortalBlock
extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_AABB = Block.makeCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    protected static final VoxelShape Z_AABB = Block.makeCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

    public NetherPortalBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(AXIS, Direction.Axis.X));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        switch (blockState.get(AXIS)) {
            case Z: {
                return Z_AABB;
            }
        }
        return X_AABB;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (serverWorld.getDimensionType().isNatural() && serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && random2.nextInt(2000) < serverWorld.getDifficulty().getId()) {
            ZombifiedPiglinEntity zombifiedPiglinEntity;
            while (serverWorld.getBlockState(blockPos).isIn(this)) {
                blockPos = blockPos.down();
            }
            if (serverWorld.getBlockState(blockPos).canEntitySpawn(serverWorld, blockPos, EntityType.ZOMBIFIED_PIGLIN) && (zombifiedPiglinEntity = EntityType.ZOMBIFIED_PIGLIN.spawn(serverWorld, null, null, null, blockPos.up(), SpawnReason.STRUCTURE, false, true)) != null) {
                zombifiedPiglinEntity.func_242279_ag();
            }
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        Direction.Axis axis = direction.getAxis();
        Direction.Axis axis2 = blockState.get(AXIS);
        boolean bl = axis2 != axis && axis.isHorizontal();
        return !bl && !blockState2.isIn(this) && !new PortalSize(iWorld, blockPos, axis2).validatePortal() ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        if (!entity2.isPassenger() && !entity2.isBeingRidden() && entity2.isNonBoss()) {
            entity2.setPortal(blockPos);
        }
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        if (random2.nextInt(100) == 0) {
            world.playSound((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5f, random2.nextFloat() * 0.4f + 0.8f, true);
        }
        for (int i = 0; i < 4; ++i) {
            double d = (double)blockPos.getX() + random2.nextDouble();
            double d2 = (double)blockPos.getY() + random2.nextDouble();
            double d3 = (double)blockPos.getZ() + random2.nextDouble();
            double d4 = ((double)random2.nextFloat() - 0.5) * 0.5;
            double d5 = ((double)random2.nextFloat() - 0.5) * 0.5;
            double d6 = ((double)random2.nextFloat() - 0.5) * 0.5;
            int n = random2.nextInt(2) * 2 - 1;
            if (!world.getBlockState(blockPos.west()).isIn(this) && !world.getBlockState(blockPos.east()).isIn(this)) {
                d = (double)blockPos.getX() + 0.5 + 0.25 * (double)n;
                d4 = random2.nextFloat() * 2.0f * (float)n;
            } else {
                d3 = (double)blockPos.getZ() + 0.5 + 0.25 * (double)n;
                d6 = random2.nextFloat() * 2.0f * (float)n;
            }
            world.addParticle(ParticleTypes.PORTAL, d, d2, d3, d4, d5, d6);
        }
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return ItemStack.EMPTY;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90: 
            case CLOCKWISE_90: {
                switch (blockState.get(AXIS)) {
                    case Z: {
                        return (BlockState)blockState.with(AXIS, Direction.Axis.X);
                    }
                    case X: {
                        return (BlockState)blockState.with(AXIS, Direction.Axis.Z);
                    }
                }
                return blockState;
            }
        }
        return blockState;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}

