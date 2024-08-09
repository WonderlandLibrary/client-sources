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
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ChorusFlowerBlock
extends Block {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_5;
    private final ChorusPlantBlock plantBlock;

    protected ChorusFlowerBlock(ChorusPlantBlock chorusPlantBlock, AbstractBlock.Properties properties) {
        super(properties);
        this.plantBlock = chorusPlantBlock;
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(AGE, 0));
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!blockState.isValidPosition(serverWorld, blockPos)) {
            serverWorld.destroyBlock(blockPos, false);
        }
    }

    @Override
    public boolean ticksRandomly(BlockState blockState) {
        return blockState.get(AGE) < 5;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        int n;
        BlockPos blockPos2 = blockPos.up();
        if (serverWorld.isAirBlock(blockPos2) && blockPos2.getY() < 256 && (n = blockState.get(AGE).intValue()) < 5) {
            int n2;
            int n3;
            boolean bl = false;
            boolean bl2 = false;
            BlockState blockState2 = serverWorld.getBlockState(blockPos.down());
            Block block = blockState2.getBlock();
            if (block == Blocks.END_STONE) {
                bl = true;
            } else if (block == this.plantBlock) {
                n3 = 1;
                for (n2 = 0; n2 < 4; ++n2) {
                    Block block2 = serverWorld.getBlockState(blockPos.down(n3 + 1)).getBlock();
                    if (block2 != this.plantBlock) {
                        if (block2 != Blocks.END_STONE) break;
                        bl2 = true;
                        break;
                    }
                    ++n3;
                }
                if (n3 < 2 || n3 <= random2.nextInt(bl2 ? 5 : 4)) {
                    bl = true;
                }
            } else if (blockState2.isAir()) {
                bl = true;
            }
            if (bl && ChorusFlowerBlock.areAllNeighborsEmpty(serverWorld, blockPos2, null) && serverWorld.isAirBlock(blockPos.up(2))) {
                serverWorld.setBlockState(blockPos, this.plantBlock.makeConnections(serverWorld, blockPos), 1);
                this.placeGrownFlower(serverWorld, blockPos2, n);
            } else if (n < 4) {
                n3 = random2.nextInt(4);
                if (bl2) {
                    ++n3;
                }
                n2 = 0;
                for (int i = 0; i < n3; ++i) {
                    Direction direction = Direction.Plane.HORIZONTAL.random(random2);
                    BlockPos blockPos3 = blockPos.offset(direction);
                    if (!serverWorld.isAirBlock(blockPos3) || !serverWorld.isAirBlock(blockPos3.down()) || !ChorusFlowerBlock.areAllNeighborsEmpty(serverWorld, blockPos3, direction.getOpposite())) continue;
                    this.placeGrownFlower(serverWorld, blockPos3, n + 1);
                    n2 = 1;
                }
                if (n2 != 0) {
                    serverWorld.setBlockState(blockPos, this.plantBlock.makeConnections(serverWorld, blockPos), 1);
                } else {
                    this.placeDeadFlower(serverWorld, blockPos);
                }
            } else {
                this.placeDeadFlower(serverWorld, blockPos);
            }
        }
    }

    private void placeGrownFlower(World world, BlockPos blockPos, int n) {
        world.setBlockState(blockPos, (BlockState)this.getDefaultState().with(AGE, n), 1);
        world.playEvent(1033, blockPos, 0);
    }

    private void placeDeadFlower(World world, BlockPos blockPos) {
        world.setBlockState(blockPos, (BlockState)this.getDefaultState().with(AGE, 5), 1);
        world.playEvent(1034, blockPos, 0);
    }

    private static boolean areAllNeighborsEmpty(IWorldReader iWorldReader, BlockPos blockPos, @Nullable Direction direction) {
        for (Direction direction2 : Direction.Plane.HORIZONTAL) {
            if (direction2 == direction || iWorldReader.isAirBlock(blockPos.offset(direction2))) continue;
            return true;
        }
        return false;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (direction != Direction.UP && !blockState.isValidPosition(iWorld, blockPos)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockState blockState2 = iWorldReader.getBlockState(blockPos.down());
        if (blockState2.getBlock() != this.plantBlock && !blockState2.isIn(Blocks.END_STONE)) {
            if (!blockState2.isAir()) {
                return true;
            }
            boolean bl = false;
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockState3 = iWorldReader.getBlockState(blockPos.offset(direction));
                if (blockState3.isIn(this.plantBlock)) {
                    if (bl) {
                        return true;
                    }
                    bl = true;
                    continue;
                }
                if (blockState3.isAir()) continue;
                return true;
            }
            return bl;
        }
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public static void generatePlant(IWorld iWorld, BlockPos blockPos, Random random2, int n) {
        iWorld.setBlockState(blockPos, ((ChorusPlantBlock)Blocks.CHORUS_PLANT).makeConnections(iWorld, blockPos), 2);
        ChorusFlowerBlock.growTreeRecursive(iWorld, blockPos, random2, blockPos, n, 0);
    }

    private static void growTreeRecursive(IWorld iWorld, BlockPos blockPos, Random random2, BlockPos blockPos2, int n, int n2) {
        int n3;
        ChorusPlantBlock chorusPlantBlock = (ChorusPlantBlock)Blocks.CHORUS_PLANT;
        int n4 = random2.nextInt(4) + 1;
        if (n2 == 0) {
            ++n4;
        }
        for (n3 = 0; n3 < n4; ++n3) {
            BlockPos blockPos3 = blockPos.up(n3 + 1);
            if (!ChorusFlowerBlock.areAllNeighborsEmpty(iWorld, blockPos3, null)) {
                return;
            }
            iWorld.setBlockState(blockPos3, chorusPlantBlock.makeConnections(iWorld, blockPos3), 2);
            iWorld.setBlockState(blockPos3.down(), chorusPlantBlock.makeConnections(iWorld, blockPos3.down()), 2);
        }
        n3 = 0;
        if (n2 < 4) {
            int n5 = random2.nextInt(4);
            if (n2 == 0) {
                ++n5;
            }
            for (int i = 0; i < n5; ++i) {
                Direction direction = Direction.Plane.HORIZONTAL.random(random2);
                BlockPos blockPos4 = blockPos.up(n4).offset(direction);
                if (Math.abs(blockPos4.getX() - blockPos2.getX()) >= n || Math.abs(blockPos4.getZ() - blockPos2.getZ()) >= n || !iWorld.isAirBlock(blockPos4) || !iWorld.isAirBlock(blockPos4.down()) || !ChorusFlowerBlock.areAllNeighborsEmpty(iWorld, blockPos4, direction.getOpposite())) continue;
                n3 = 1;
                iWorld.setBlockState(blockPos4, chorusPlantBlock.makeConnections(iWorld, blockPos4), 2);
                iWorld.setBlockState(blockPos4.offset(direction.getOpposite()), chorusPlantBlock.makeConnections(iWorld, blockPos4.offset(direction.getOpposite())), 2);
                ChorusFlowerBlock.growTreeRecursive(iWorld, blockPos4, random2, blockPos2, n, n2 + 1);
            }
        }
        if (n3 == 0) {
            iWorld.setBlockState(blockPos.up(n4), (BlockState)Blocks.CHORUS_FLOWER.getDefaultState().with(AGE, 5), 2);
        }
    }

    @Override
    public void onProjectileCollision(World world, BlockState blockState, BlockRayTraceResult blockRayTraceResult, ProjectileEntity projectileEntity) {
        if (projectileEntity.getType().isContained(EntityTypeTags.IMPACT_PROJECTILES)) {
            BlockPos blockPos = blockRayTraceResult.getPos();
            world.destroyBlock(blockPos, true, projectileEntity);
        }
    }
}

