/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;

public class RandomWalkingGoal
extends Goal {
    protected final CreatureEntity creature;
    protected double x;
    protected double y;
    protected double z;
    protected final double speed;
    protected int executionChance;
    protected boolean mustUpdate;
    private boolean field_234053_h_;

    public RandomWalkingGoal(CreatureEntity creatureEntity, double d) {
        this(creatureEntity, d, 120);
    }

    public RandomWalkingGoal(CreatureEntity creatureEntity, double d, int n) {
        this(creatureEntity, d, n, true);
    }

    public RandomWalkingGoal(CreatureEntity creatureEntity, double d, int n, boolean bl) {
        this.creature = creatureEntity;
        this.speed = d;
        this.executionChance = n;
        this.field_234053_h_ = bl;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        Vector3d vector3d;
        if (this.creature.isBeingRidden()) {
            return true;
        }
        if (!this.mustUpdate) {
            if (this.field_234053_h_ && this.creature.getIdleTime() >= 100) {
                return true;
            }
            if (this.creature.getRNG().nextInt(this.executionChance) != 0) {
                return true;
            }
        }
        if ((vector3d = this.getPosition()) == null) {
            return true;
        }
        this.x = vector3d.x;
        this.y = vector3d.y;
        this.z = vector3d.z;
        this.mustUpdate = false;
        return false;
    }

    @Nullable
    protected Vector3d getPosition() {
        return RandomPositionGenerator.findRandomTarget(this.creature, 10, 7);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.creature.getNavigator().noPath() && !this.creature.isBeingRidden();
    }

    @Override
    public void startExecuting() {
        this.creature.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
    }

    @Override
    public void resetTask() {
        this.creature.getNavigator().clearPath();
        super.resetTask();
    }

    public void makeUpdate() {
        this.mustUpdate = true;
    }

    public void setExecutionChance(int n) {
        this.executionChance = n;
    }
}

