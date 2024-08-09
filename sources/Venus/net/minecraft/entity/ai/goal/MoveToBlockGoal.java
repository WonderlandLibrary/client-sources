/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public abstract class MoveToBlockGoal
extends Goal {
    protected final CreatureEntity creature;
    public final double movementSpeed;
    protected int runDelay;
    protected int timeoutCounter;
    private int maxStayTicks;
    protected BlockPos destinationBlock = BlockPos.ZERO;
    private boolean isAboveDestination;
    private final int searchLength;
    private final int field_203113_j;
    protected int field_203112_e;

    public MoveToBlockGoal(CreatureEntity creatureEntity, double d, int n) {
        this(creatureEntity, d, n, 1);
    }

    public MoveToBlockGoal(CreatureEntity creatureEntity, double d, int n, int n2) {
        this.creature = creatureEntity;
        this.movementSpeed = d;
        this.searchLength = n;
        this.field_203112_e = 0;
        this.field_203113_j = n2;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean shouldExecute() {
        if (this.runDelay > 0) {
            --this.runDelay;
            return true;
        }
        this.runDelay = this.getRunDelay(this.creature);
        return this.searchForDestination();
    }

    protected int getRunDelay(CreatureEntity creatureEntity) {
        return 200 + creatureEntity.getRNG().nextInt(200);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.creature.world, this.destinationBlock);
    }

    @Override
    public void startExecuting() {
        this.func_220725_g();
        this.timeoutCounter = 0;
        this.maxStayTicks = this.creature.getRNG().nextInt(this.creature.getRNG().nextInt(1200) + 1200) + 1200;
    }

    protected void func_220725_g() {
        this.creature.getNavigator().tryMoveToXYZ((double)this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + 1, (double)this.destinationBlock.getZ() + 0.5, this.movementSpeed);
    }

    public double getTargetDistanceSq() {
        return 1.0;
    }

    protected BlockPos func_241846_j() {
        return this.destinationBlock.up();
    }

    @Override
    public void tick() {
        BlockPos blockPos = this.func_241846_j();
        if (!blockPos.withinDistance(this.creature.getPositionVec(), this.getTargetDistanceSq())) {
            this.isAboveDestination = false;
            ++this.timeoutCounter;
            if (this.shouldMove()) {
                this.creature.getNavigator().tryMoveToXYZ((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, this.movementSpeed);
            }
        } else {
            this.isAboveDestination = true;
            --this.timeoutCounter;
        }
    }

    public boolean shouldMove() {
        return this.timeoutCounter % 40 == 0;
    }

    protected boolean getIsAboveDestination() {
        return this.isAboveDestination;
    }

    protected boolean searchForDestination() {
        int n = this.searchLength;
        int n2 = this.field_203113_j;
        BlockPos blockPos = this.creature.getPosition();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int n3 = this.field_203112_e;
        while (n3 <= n2) {
            for (int i = 0; i < n; ++i) {
                int n4 = 0;
                while (n4 <= i) {
                    int n5;
                    int n6 = n5 = n4 < i && n4 > -i ? i : 0;
                    while (n5 <= i) {
                        mutable.setAndOffset(blockPos, n4, n3 - 1, n5);
                        if (this.creature.isWithinHomeDistanceFromPosition(mutable) && this.shouldMoveTo(this.creature.world, mutable)) {
                            this.destinationBlock = mutable;
                            return false;
                        }
                        n5 = n5 > 0 ? -n5 : 1 - n5;
                    }
                    n4 = n4 > 0 ? -n4 : 1 - n4;
                }
            }
            n3 = n3 > 0 ? -n3 : 1 - n3;
        }
        return true;
    }

    protected abstract boolean shouldMoveTo(IWorldReader var1, BlockPos var2);
}

