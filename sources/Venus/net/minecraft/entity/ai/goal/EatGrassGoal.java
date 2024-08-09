/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EatGrassGoal
extends Goal {
    private static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);
    private final MobEntity grassEaterEntity;
    private final World entityWorld;
    private int eatingGrassTimer;

    public EatGrassGoal(MobEntity mobEntity) {
        this.grassEaterEntity = mobEntity;
        this.entityWorld = mobEntity.world;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean shouldExecute() {
        if (this.grassEaterEntity.getRNG().nextInt(this.grassEaterEntity.isChild() ? 50 : 1000) != 0) {
            return true;
        }
        BlockPos blockPos = this.grassEaterEntity.getPosition();
        if (IS_GRASS.test(this.entityWorld.getBlockState(blockPos))) {
            return false;
        }
        return this.entityWorld.getBlockState(blockPos.down()).isIn(Blocks.GRASS_BLOCK);
    }

    @Override
    public void startExecuting() {
        this.eatingGrassTimer = 40;
        this.entityWorld.setEntityState(this.grassEaterEntity, (byte)10);
        this.grassEaterEntity.getNavigator().clearPath();
    }

    @Override
    public void resetTask() {
        this.eatingGrassTimer = 0;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.eatingGrassTimer > 0;
    }

    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }

    @Override
    public void tick() {
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
        if (this.eatingGrassTimer == 4) {
            BlockPos blockPos = this.grassEaterEntity.getPosition();
            if (IS_GRASS.test(this.entityWorld.getBlockState(blockPos))) {
                if (this.entityWorld.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
                    this.entityWorld.destroyBlock(blockPos, true);
                }
                this.grassEaterEntity.eatGrassBonus();
            } else {
                BlockPos blockPos2 = blockPos.down();
                if (this.entityWorld.getBlockState(blockPos2).isIn(Blocks.GRASS_BLOCK)) {
                    if (this.entityWorld.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
                        this.entityWorld.playEvent(2001, blockPos2, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                        this.entityWorld.setBlockState(blockPos2, Blocks.DIRT.getDefaultState(), 1);
                    }
                    this.grassEaterEntity.eatGrassBonus();
                }
            }
        }
    }
}

