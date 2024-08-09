/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.PortalSize;
import net.minecraft.block.SoulFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class AbstractFireBlock
extends Block {
    private final float fireDamage;
    protected static final VoxelShape shapeDown = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);

    public AbstractFireBlock(AbstractBlock.Properties properties, float f) {
        super(properties);
        this.fireDamage = f;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return AbstractFireBlock.getFireForPlacement(blockItemUseContext.getWorld(), blockItemUseContext.getPos());
    }

    public static BlockState getFireForPlacement(IBlockReader iBlockReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        BlockState blockState = iBlockReader.getBlockState(blockPos2);
        return SoulFireBlock.shouldLightSoulFire(blockState.getBlock()) ? Blocks.SOUL_FIRE.getDefaultState() : ((FireBlock)Blocks.FIRE).getStateForPlacement(iBlockReader, blockPos);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return shapeDown;
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        block12: {
            block11: {
                double d;
                double d2;
                double d3;
                int n;
                BlockPos blockPos2;
                BlockState blockState2;
                if (random2.nextInt(24) == 0) {
                    world.playSound((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random2.nextFloat(), random2.nextFloat() * 0.7f + 0.3f, true);
                }
                if (this.canBurn(blockState2 = world.getBlockState(blockPos2 = blockPos.down())) || blockState2.isSolidSide(world, blockPos2, Direction.UP)) break block11;
                if (this.canBurn(world.getBlockState(blockPos.west()))) {
                    for (n = 0; n < 2; ++n) {
                        d3 = (double)blockPos.getX() + random2.nextDouble() * (double)0.1f;
                        d2 = (double)blockPos.getY() + random2.nextDouble();
                        d = (double)blockPos.getZ() + random2.nextDouble();
                        world.addParticle(ParticleTypes.LARGE_SMOKE, d3, d2, d, 0.0, 0.0, 0.0);
                    }
                }
                if (this.canBurn(world.getBlockState(blockPos.east()))) {
                    for (n = 0; n < 2; ++n) {
                        d3 = (double)(blockPos.getX() + 1) - random2.nextDouble() * (double)0.1f;
                        d2 = (double)blockPos.getY() + random2.nextDouble();
                        d = (double)blockPos.getZ() + random2.nextDouble();
                        world.addParticle(ParticleTypes.LARGE_SMOKE, d3, d2, d, 0.0, 0.0, 0.0);
                    }
                }
                if (this.canBurn(world.getBlockState(blockPos.north()))) {
                    for (n = 0; n < 2; ++n) {
                        d3 = (double)blockPos.getX() + random2.nextDouble();
                        d2 = (double)blockPos.getY() + random2.nextDouble();
                        d = (double)blockPos.getZ() + random2.nextDouble() * (double)0.1f;
                        world.addParticle(ParticleTypes.LARGE_SMOKE, d3, d2, d, 0.0, 0.0, 0.0);
                    }
                }
                if (this.canBurn(world.getBlockState(blockPos.south()))) {
                    for (n = 0; n < 2; ++n) {
                        d3 = (double)blockPos.getX() + random2.nextDouble();
                        d2 = (double)blockPos.getY() + random2.nextDouble();
                        d = (double)(blockPos.getZ() + 1) - random2.nextDouble() * (double)0.1f;
                        world.addParticle(ParticleTypes.LARGE_SMOKE, d3, d2, d, 0.0, 0.0, 0.0);
                    }
                }
                if (!this.canBurn(world.getBlockState(blockPos.up()))) break block12;
                for (n = 0; n < 2; ++n) {
                    d3 = (double)blockPos.getX() + random2.nextDouble();
                    d2 = (double)(blockPos.getY() + 1) - random2.nextDouble() * (double)0.1f;
                    d = (double)blockPos.getZ() + random2.nextDouble();
                    world.addParticle(ParticleTypes.LARGE_SMOKE, d3, d2, d, 0.0, 0.0, 0.0);
                }
                break block12;
            }
            for (int i = 0; i < 3; ++i) {
                double d = (double)blockPos.getX() + random2.nextDouble();
                double d4 = (double)blockPos.getY() + random2.nextDouble() * 0.5 + 0.5;
                double d5 = (double)blockPos.getZ() + random2.nextDouble();
                world.addParticle(ParticleTypes.LARGE_SMOKE, d, d4, d5, 0.0, 0.0, 0.0);
            }
        }
    }

    protected abstract boolean canBurn(BlockState var1);

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        if (!entity2.isImmuneToFire()) {
            entity2.forceFireTicks(entity2.getFireTimer() + 1);
            if (entity2.getFireTimer() == 0) {
                entity2.setFire(8);
            }
            entity2.attackEntityFrom(DamageSource.IN_FIRE, this.fireDamage);
        }
        super.onEntityCollision(blockState, world, blockPos, entity2);
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.isIn(blockState.getBlock())) {
            Optional<PortalSize> optional;
            if (AbstractFireBlock.canLightPortal(world) && (optional = PortalSize.func_242964_a(world, blockPos, Direction.Axis.X)).isPresent()) {
                optional.get().placePortalBlocks();
                return;
            }
            if (!blockState.isValidPosition(world, blockPos)) {
                world.removeBlock(blockPos, true);
            }
        }
    }

    private static boolean canLightPortal(World world) {
        return world.getDimensionKey() == World.OVERWORLD || world.getDimensionKey() == World.THE_NETHER;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if (!world.isRemote()) {
            world.playEvent(null, 1009, blockPos, 0);
        }
    }

    public static boolean canLightBlock(World world, BlockPos blockPos, Direction direction) {
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.isAir()) {
            return true;
        }
        return AbstractFireBlock.getFireForPlacement(world, blockPos).isValidPosition(world, blockPos) || AbstractFireBlock.shouldLightPortal(world, blockPos, direction);
    }

    private static boolean shouldLightPortal(World world, BlockPos blockPos, Direction direction) {
        if (!AbstractFireBlock.canLightPortal(world)) {
            return true;
        }
        BlockPos.Mutable mutable = blockPos.toMutable();
        boolean bl = false;
        for (Direction direction2 : Direction.values()) {
            if (!world.getBlockState(mutable.setPos(blockPos).move(direction2)).isIn(Blocks.OBSIDIAN)) continue;
            bl = true;
            break;
        }
        if (!bl) {
            return true;
        }
        Direction.Axis axis = direction.getAxis().isHorizontal() ? direction.rotateYCCW().getAxis() : Direction.Plane.HORIZONTAL.func_244803_b(world.rand);
        return PortalSize.func_242964_a(world, blockPos, axis).isPresent();
    }
}

