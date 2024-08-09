/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.BowItem;
import net.minecraft.item.Items;

public class RangedBowAttackGoal<T extends MonsterEntity>
extends Goal {
    private final T entity;
    private final double moveSpeedAmp;
    private int attackCooldown;
    private final float maxAttackDistance;
    private int attackTime = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public RangedBowAttackGoal(T t, double d, int n, float f) {
        this.entity = t;
        this.moveSpeedAmp = d;
        this.attackCooldown = n;
        this.maxAttackDistance = f * f;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public void setAttackCooldown(int n) {
        this.attackCooldown = n;
    }

    @Override
    public boolean shouldExecute() {
        return ((MobEntity)this.entity).getAttackTarget() == null ? false : this.isBowInMainhand();
    }

    protected boolean isBowInMainhand() {
        return ((LivingEntity)this.entity).canEquip(Items.BOW);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return (this.shouldExecute() || !((MobEntity)this.entity).getNavigator().noPath()) && this.isBowInMainhand();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        ((MobEntity)this.entity).setAggroed(false);
    }

    @Override
    public void resetTask() {
        super.resetTask();
        ((MobEntity)this.entity).setAggroed(true);
        this.seeTime = 0;
        this.attackTime = -1;
        ((LivingEntity)this.entity).resetActiveHand();
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = ((MobEntity)this.entity).getAttackTarget();
        if (livingEntity != null) {
            boolean bl;
            double d = ((Entity)this.entity).getDistanceSq(livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ());
            boolean bl2 = ((MobEntity)this.entity).getEntitySenses().canSee(livingEntity);
            boolean bl3 = bl = this.seeTime > 0;
            if (bl2 != bl) {
                this.seeTime = 0;
            }
            this.seeTime = bl2 ? ++this.seeTime : --this.seeTime;
            if (!(d > (double)this.maxAttackDistance) && this.seeTime >= 20) {
                ((MobEntity)this.entity).getNavigator().clearPath();
                ++this.strafingTime;
            } else {
                ((MobEntity)this.entity).getNavigator().tryMoveToEntityLiving(livingEntity, this.moveSpeedAmp);
                this.strafingTime = -1;
            }
            if (this.strafingTime >= 20) {
                if ((double)((LivingEntity)this.entity).getRNG().nextFloat() < 0.3) {
                    boolean bl4 = this.strafingClockwise = !this.strafingClockwise;
                }
                if ((double)((LivingEntity)this.entity).getRNG().nextFloat() < 0.3) {
                    this.strafingBackwards = !this.strafingBackwards;
                }
                this.strafingTime = 0;
            }
            if (this.strafingTime > -1) {
                if (d > (double)(this.maxAttackDistance * 0.75f)) {
                    this.strafingBackwards = false;
                } else if (d < (double)(this.maxAttackDistance * 0.25f)) {
                    this.strafingBackwards = true;
                }
                ((MobEntity)this.entity).getMoveHelper().strafe(this.strafingBackwards ? -0.5f : 0.5f, this.strafingClockwise ? 0.5f : -0.5f);
                ((MobEntity)this.entity).faceEntity(livingEntity, 30.0f, 30.0f);
            } else {
                ((MobEntity)this.entity).getLookController().setLookPositionWithEntity(livingEntity, 30.0f, 30.0f);
            }
            if (((LivingEntity)this.entity).isHandActive()) {
                int n;
                if (!bl2 && this.seeTime < -60) {
                    ((LivingEntity)this.entity).resetActiveHand();
                } else if (bl2 && (n = ((LivingEntity)this.entity).getItemInUseMaxCount()) >= 20) {
                    ((LivingEntity)this.entity).resetActiveHand();
                    ((IRangedAttackMob)this.entity).attackEntityWithRangedAttack(livingEntity, BowItem.getArrowVelocity(n));
                    this.attackTime = this.attackCooldown;
                }
            } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
                ((LivingEntity)this.entity).setActiveHand(ProjectileHelper.getHandWith(this.entity, Items.BOW));
            }
        }
    }
}

