/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.IBlockReader;

public class OcelotAttackGoal
extends Goal {
    private final IBlockReader world;
    private final MobEntity entity;
    private LivingEntity target;
    private int attackCountdown;

    public OcelotAttackGoal(MobEntity mobEntity) {
        this.entity = mobEntity;
        this.world = mobEntity.world;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity livingEntity = this.entity.getAttackTarget();
        if (livingEntity == null) {
            return true;
        }
        this.target = livingEntity;
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (!this.target.isAlive()) {
            return true;
        }
        if (this.entity.getDistanceSq(this.target) > 225.0) {
            return true;
        }
        return !this.entity.getNavigator().noPath() || this.shouldExecute();
    }

    @Override
    public void resetTask() {
        this.target = null;
        this.entity.getNavigator().clearPath();
    }

    @Override
    public void tick() {
        this.entity.getLookController().setLookPositionWithEntity(this.target, 30.0f, 30.0f);
        double d = this.entity.getWidth() * 2.0f * this.entity.getWidth() * 2.0f;
        double d2 = this.entity.getDistanceSq(this.target.getPosX(), this.target.getPosY(), this.target.getPosZ());
        double d3 = 0.8;
        if (d2 > d && d2 < 16.0) {
            d3 = 1.33;
        } else if (d2 < 225.0) {
            d3 = 0.6;
        }
        this.entity.getNavigator().tryMoveToEntityLiving(this.target, d3);
        this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
        if (!(d2 > d) && this.attackCountdown <= 0) {
            this.attackCountdown = 20;
            this.entity.attackEntityAsMob(this.target);
        }
    }
}

