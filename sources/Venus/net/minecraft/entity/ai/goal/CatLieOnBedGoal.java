/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class CatLieOnBedGoal
extends MoveToBlockGoal {
    private final CatEntity cat;

    public CatLieOnBedGoal(CatEntity catEntity, double d, int n) {
        super(catEntity, d, n, 6);
        this.cat = catEntity;
        this.field_203112_e = -2;
        this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        return this.cat.isTamed() && !this.cat.isSitting() && !this.cat.func_213416_eg() && super.shouldExecute();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.cat.setSleeping(true);
    }

    @Override
    protected int getRunDelay(CreatureEntity creatureEntity) {
        return 1;
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.cat.func_213419_u(true);
    }

    @Override
    public void tick() {
        super.tick();
        this.cat.setSleeping(true);
        if (!this.getIsAboveDestination()) {
            this.cat.func_213419_u(true);
        } else if (!this.cat.func_213416_eg()) {
            this.cat.func_213419_u(false);
        }
    }

    @Override
    protected boolean shouldMoveTo(IWorldReader iWorldReader, BlockPos blockPos) {
        return iWorldReader.isAirBlock(blockPos.up()) && iWorldReader.getBlockState(blockPos).getBlock().isIn(BlockTags.BEDS);
    }
}

