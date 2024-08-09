/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;

public class LeapAtTargetGoal
extends Goal {
    private final MobEntity leaper;
    private LivingEntity leapTarget;
    private final float leapMotionY;

    public LeapAtTargetGoal(MobEntity mobEntity, float f) {
        this.leaper = mobEntity;
        this.leapMotionY = f;
        this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (this.leaper.isBeingRidden()) {
            return true;
        }
        this.leapTarget = this.leaper.getAttackTarget();
        if (this.leapTarget == null) {
            return true;
        }
        double d = this.leaper.getDistanceSq(this.leapTarget);
        if (!(d < 4.0) && !(d > 16.0)) {
            if (!this.leaper.isOnGround()) {
                return true;
            }
            return this.leaper.getRNG().nextInt(5) == 0;
        }
        return true;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.leaper.isOnGround();
    }

    @Override
    public void startExecuting() {
        Vector3d vector3d = this.leaper.getMotion();
        Vector3d vector3d2 = new Vector3d(this.leapTarget.getPosX() - this.leaper.getPosX(), 0.0, this.leapTarget.getPosZ() - this.leaper.getPosZ());
        if (vector3d2.lengthSquared() > 1.0E-7) {
            vector3d2 = vector3d2.normalize().scale(0.4).add(vector3d.scale(0.2));
        }
        this.leaper.setMotion(vector3d2.x, this.leapMotionY, vector3d2.z);
    }
}

