/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;

public class BreakBlockGoal
extends MoveToBlockGoal {
    private final Block block;
    private final MobEntity entity;
    private int breakingTime;

    public BreakBlockGoal(Block block, CreatureEntity creatureEntity, double d, int n) {
        super(creatureEntity, d, 24, n);
        this.block = block;
        this.entity = creatureEntity;
    }

    @Override
    public boolean shouldExecute() {
        if (!this.entity.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
            return true;
        }
        if (this.runDelay > 0) {
            --this.runDelay;
            return true;
        }
        if (this.func_220729_m()) {
            this.runDelay = 20;
            return false;
        }
        this.runDelay = this.getRunDelay(this.creature);
        return true;
    }

    private boolean func_220729_m() {
        return this.destinationBlock != null && this.shouldMoveTo(this.creature.world, this.destinationBlock) ? true : this.searchForDestination();
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.entity.fallDistance = 1.0f;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.breakingTime = 0;
    }

    public void playBreakingSound(IWorld iWorld, BlockPos blockPos) {
    }

    public void playBrokenSound(World world, BlockPos blockPos) {
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.entity.world;
        BlockPos blockPos = this.entity.getPosition();
        BlockPos blockPos2 = this.findTarget(blockPos, world);
        Random random2 = this.entity.getRNG();
        if (this.getIsAboveDestination() && blockPos2 != null) {
            double d;
            Vector3d vector3d;
            if (this.breakingTime > 0) {
                vector3d = this.entity.getMotion();
                this.entity.setMotion(vector3d.x, 0.3, vector3d.z);
                if (!world.isRemote) {
                    d = 0.08;
                    ((ServerWorld)world).spawnParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.EGG)), (double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.7, (double)blockPos2.getZ() + 0.5, 3, ((double)random2.nextFloat() - 0.5) * 0.08, ((double)random2.nextFloat() - 0.5) * 0.08, ((double)random2.nextFloat() - 0.5) * 0.08, 0.15f);
                }
            }
            if (this.breakingTime % 2 == 0) {
                vector3d = this.entity.getMotion();
                this.entity.setMotion(vector3d.x, -0.3, vector3d.z);
                if (this.breakingTime % 6 == 0) {
                    this.playBreakingSound(world, this.destinationBlock);
                }
            }
            if (this.breakingTime > 60) {
                world.removeBlock(blockPos2, true);
                if (!world.isRemote) {
                    for (int i = 0; i < 20; ++i) {
                        d = random2.nextGaussian() * 0.02;
                        double d2 = random2.nextGaussian() * 0.02;
                        double d3 = random2.nextGaussian() * 0.02;
                        ((ServerWorld)world).spawnParticle(ParticleTypes.POOF, (double)blockPos2.getX() + 0.5, blockPos2.getY(), (double)blockPos2.getZ() + 0.5, 1, d, d2, d3, 0.15f);
                    }
                    this.playBrokenSound(world, blockPos2);
                }
            }
            ++this.breakingTime;
        }
    }

    @Nullable
    private BlockPos findTarget(BlockPos blockPos, IBlockReader iBlockReader) {
        BlockPos[] blockPosArray;
        if (iBlockReader.getBlockState(blockPos).isIn(this.block)) {
            return blockPos;
        }
        for (BlockPos blockPos2 : blockPosArray = new BlockPos[]{blockPos.down(), blockPos.west(), blockPos.east(), blockPos.north(), blockPos.south(), blockPos.down().down()}) {
            if (!iBlockReader.getBlockState(blockPos2).isIn(this.block)) continue;
            return blockPos2;
        }
        return null;
    }

    @Override
    protected boolean shouldMoveTo(IWorldReader iWorldReader, BlockPos blockPos) {
        IChunk iChunk = iWorldReader.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4, ChunkStatus.FULL, false);
        if (iChunk == null) {
            return true;
        }
        return iChunk.getBlockState(blockPos).isIn(this.block) && iChunk.getBlockState(blockPos.up()).isAir() && iChunk.getBlockState(blockPos.up(2)).isAir();
    }
}

