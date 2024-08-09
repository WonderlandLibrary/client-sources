/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

public class RangedAttackGoal
extends Goal {
    private final MobEntity entityHost;
    private final IRangedAttackMob rangedAttackEntityHost;
    private LivingEntity attackTarget;
    private int rangedAttackTime = -1;
    private final double entityMoveSpeed;
    private int seeTime;
    private final int attackIntervalMin;
    private final int maxRangedAttackTime;
    private final float attackRadius;
    private final float maxAttackDistance;

    public RangedAttackGoal(IRangedAttackMob iRangedAttackMob, double d, int n, float f) {
        this(iRangedAttackMob, d, n, n, f);
    }

    public RangedAttackGoal(IRangedAttackMob iRangedAttackMob, double d, int n, int n2, float f) {
        if (!(iRangedAttackMob instanceof LivingEntity)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        this.rangedAttackEntityHost = iRangedAttackMob;
        this.entityHost = (MobEntity)((Object)iRangedAttackMob);
        this.entityMoveSpeed = d;
        this.attackIntervalMin = n;
        this.maxRangedAttackTime = n2;
        this.attackRadius = f;
        this.maxAttackDistance = f * f;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity livingEntity = this.entityHost.getAttackTarget();
        if (livingEntity != null && livingEntity.isAlive()) {
            this.attackTarget = livingEntity;
            return false;
        }
        return true;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }

    @Override
    public void resetTask() {
        this.attackTarget = null;
        this.seeTime = 0;
        this.rangedAttackTime = -1;
    }

    @Override
    public void tick() {
        double d = this.entityHost.getDistanceSq(this.attackTarget.getPosX(), this.attackTarget.getPosY(), this.attackTarget.getPosZ());
        boolean bl = this.entityHost.getEntitySenses().canSee(this.attackTarget);
        this.seeTime = bl ? ++this.seeTime : 0;
        if (!(d > (double)this.maxAttackDistance) && this.seeTime >= 5) {
            this.entityHost.getNavigator().clearPath();
        } else {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }
        this.entityHost.getLookController().setLookPositionWithEntity(this.attackTarget, 30.0f, 30.0f);
        if (--this.rangedAttackTime == 0) {
            if (!bl) {
                return;
            }
            float f = MathHelper.sqrt(d) / this.attackRadius;
            float f2 = MathHelper.clamp(f, 0.1f, 1.0f);
            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, f2);
            this.rangedAttackTime = MathHelper.floor(f * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
        } else if (this.rangedAttackTime < 0) {
            float f = MathHelper.sqrt(d) / this.attackRadius;
            this.rangedAttackTime = MathHelper.floor(f * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
        }
    }
}

