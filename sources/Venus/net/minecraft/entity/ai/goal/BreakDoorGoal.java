/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.InteractDoorGoal;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;

public class BreakDoorGoal
extends InteractDoorGoal {
    private final Predicate<Difficulty> difficultyPredicate;
    protected int breakingTime;
    protected int previousBreakProgress = -1;
    protected int timeToBreak = -1;

    public BreakDoorGoal(MobEntity mobEntity, Predicate<Difficulty> predicate) {
        super(mobEntity);
        this.difficultyPredicate = predicate;
    }

    public BreakDoorGoal(MobEntity mobEntity, int n, Predicate<Difficulty> predicate) {
        this(mobEntity, predicate);
        this.timeToBreak = n;
    }

    protected int func_220697_f() {
        return Math.max(240, this.timeToBreak);
    }

    @Override
    public boolean shouldExecute() {
        if (!super.shouldExecute()) {
            return true;
        }
        if (!this.entity.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
            return true;
        }
        return this.func_220696_a(this.entity.world.getDifficulty()) && !this.canDestroy();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.breakingTime = 0;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.breakingTime <= this.func_220697_f() && !this.canDestroy() && this.doorPosition.withinDistance(this.entity.getPositionVec(), 2.0) && this.func_220696_a(this.entity.world.getDifficulty());
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.doorPosition, -1);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.entity.getRNG().nextInt(20) == 0) {
            this.entity.world.playEvent(1019, this.doorPosition, 0);
            if (!this.entity.isSwingInProgress) {
                this.entity.swingArm(this.entity.getActiveHand());
            }
        }
        ++this.breakingTime;
        int n = (int)((float)this.breakingTime / (float)this.func_220697_f() * 10.0f);
        if (n != this.previousBreakProgress) {
            this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.doorPosition, n);
            this.previousBreakProgress = n;
        }
        if (this.breakingTime == this.func_220697_f() && this.func_220696_a(this.entity.world.getDifficulty())) {
            this.entity.world.removeBlock(this.doorPosition, true);
            this.entity.world.playEvent(1021, this.doorPosition, 0);
            this.entity.world.playEvent(2001, this.doorPosition, Block.getStateId(this.entity.world.getBlockState(this.doorPosition)));
        }
    }

    private boolean func_220696_a(Difficulty difficulty) {
        return this.difficultyPredicate.test(difficulty);
    }
}

