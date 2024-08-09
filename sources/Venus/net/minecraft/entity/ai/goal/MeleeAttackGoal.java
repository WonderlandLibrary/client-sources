/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;

public class MeleeAttackGoal
extends Goal {
    protected final CreatureEntity attacker;
    private final double speedTowardsTarget;
    private final boolean longMemory;
    private Path path;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int delayCounter;
    private int field_234037_i_;
    private final int attackInterval = 20;
    private long field_220720_k;

    public MeleeAttackGoal(CreatureEntity creatureEntity, double d, boolean bl) {
        this.attacker = creatureEntity;
        this.speedTowardsTarget = d;
        this.longMemory = bl;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        long l = this.attacker.world.getGameTime();
        if (l - this.field_220720_k < 20L) {
            return true;
        }
        this.field_220720_k = l;
        LivingEntity livingEntity = this.attacker.getAttackTarget();
        if (livingEntity == null) {
            return true;
        }
        if (!livingEntity.isAlive()) {
            return true;
        }
        this.path = this.attacker.getNavigator().getPathToEntity(livingEntity, 0);
        if (this.path != null) {
            return false;
        }
        return this.getAttackReachSqr(livingEntity) >= this.attacker.getDistanceSq(livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ());
    }

    @Override
    public boolean shouldContinueExecuting() {
        LivingEntity livingEntity = this.attacker.getAttackTarget();
        if (livingEntity == null) {
            return true;
        }
        if (!livingEntity.isAlive()) {
            return true;
        }
        if (!this.longMemory) {
            return !this.attacker.getNavigator().noPath();
        }
        if (!this.attacker.isWithinHomeDistanceFromPosition(livingEntity.getPosition())) {
            return true;
        }
        return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity)livingEntity).isCreative();
    }

    @Override
    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.path, this.speedTowardsTarget);
        this.attacker.setAggroed(false);
        this.delayCounter = 0;
        this.field_234037_i_ = 0;
    }

    @Override
    public void resetTask() {
        LivingEntity livingEntity = this.attacker.getAttackTarget();
        if (!EntityPredicates.CAN_AI_TARGET.test(livingEntity)) {
            this.attacker.setAttackTarget(null);
        }
        this.attacker.setAggroed(true);
        this.attacker.getNavigator().clearPath();
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.attacker.getAttackTarget();
        this.attacker.getLookController().setLookPositionWithEntity(livingEntity, 30.0f, 30.0f);
        double d = this.attacker.getDistanceSq(livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ());
        this.delayCounter = Math.max(this.delayCounter - 1, 0);
        if ((this.longMemory || this.attacker.getEntitySenses().canSee(livingEntity)) && this.delayCounter <= 0 && (this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0 || livingEntity.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0 || this.attacker.getRNG().nextFloat() < 0.05f)) {
            this.targetX = livingEntity.getPosX();
            this.targetY = livingEntity.getPosY();
            this.targetZ = livingEntity.getPosZ();
            this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
            if (d > 1024.0) {
                this.delayCounter += 10;
            } else if (d > 256.0) {
                this.delayCounter += 5;
            }
            if (!this.attacker.getNavigator().tryMoveToEntityLiving(livingEntity, this.speedTowardsTarget)) {
                this.delayCounter += 15;
            }
        }
        this.field_234037_i_ = Math.max(this.field_234037_i_ - 1, 0);
        this.checkAndPerformAttack(livingEntity, d);
    }

    protected void checkAndPerformAttack(LivingEntity livingEntity, double d) {
        double d2 = this.getAttackReachSqr(livingEntity);
        if (d <= d2 && this.field_234037_i_ <= 0) {
            this.func_234039_g_();
            this.attacker.swingArm(Hand.MAIN_HAND);
            this.attacker.attackEntityAsMob(livingEntity);
        }
    }

    protected void func_234039_g_() {
        this.field_234037_i_ = 20;
    }

    protected boolean func_234040_h_() {
        return this.field_234037_i_ <= 0;
    }

    protected int func_234041_j_() {
        return this.field_234037_i_;
    }

    protected int func_234042_k_() {
        return 1;
    }

    protected double getAttackReachSqr(LivingEntity livingEntity) {
        return this.attacker.getWidth() * 2.0f * this.attacker.getWidth() * 2.0f + livingEntity.getWidth();
    }
}

