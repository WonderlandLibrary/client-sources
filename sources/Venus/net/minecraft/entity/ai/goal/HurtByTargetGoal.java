/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.GameRules;

public class HurtByTargetGoal
extends TargetGoal {
    private static final EntityPredicate field_220795_a = new EntityPredicate().setLineOfSiteRequired().setUseInvisibilityCheck();
    private boolean entityCallsForHelp;
    private int revengeTimerOld;
    private final Class<?>[] excludedReinforcementTypes;
    private Class<?>[] reinforcementTypes;

    public HurtByTargetGoal(CreatureEntity creatureEntity, Class<?> ... classArray) {
        super(creatureEntity, true);
        this.excludedReinforcementTypes = classArray;
        this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean shouldExecute() {
        int n = this.goalOwner.getRevengeTimer();
        LivingEntity livingEntity = this.goalOwner.getRevengeTarget();
        if (n != this.revengeTimerOld && livingEntity != null) {
            if (livingEntity.getType() == EntityType.PLAYER && this.goalOwner.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
                return true;
            }
            for (Class<?> clazz : this.excludedReinforcementTypes) {
                if (!clazz.isAssignableFrom(livingEntity.getClass())) continue;
                return true;
            }
            return this.isSuitableTarget(livingEntity, field_220795_a);
        }
        return true;
    }

    public HurtByTargetGoal setCallsForHelp(Class<?> ... classArray) {
        this.entityCallsForHelp = true;
        this.reinforcementTypes = classArray;
        return this;
    }

    @Override
    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.goalOwner.getRevengeTarget());
        this.target = this.goalOwner.getAttackTarget();
        this.revengeTimerOld = this.goalOwner.getRevengeTimer();
        this.unseenMemoryTicks = 300;
        if (this.entityCallsForHelp) {
            this.alertOthers();
        }
        super.startExecuting();
    }

    protected void alertOthers() {
        double d = this.getTargetDistance();
        AxisAlignedBB axisAlignedBB = AxisAlignedBB.fromVector(this.goalOwner.getPositionVec()).grow(d, 10.0, d);
        List<?> list = this.goalOwner.world.getLoadedEntitiesWithinAABB(this.goalOwner.getClass(), axisAlignedBB);
        Iterator<?> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            MobEntity mobEntity = (MobEntity)iterator2.next();
            if (this.goalOwner == mobEntity || mobEntity.getAttackTarget() != null || this.goalOwner instanceof TameableEntity && ((TameableEntity)this.goalOwner).getOwner() != ((TameableEntity)mobEntity).getOwner() || mobEntity.isOnSameTeam(this.goalOwner.getRevengeTarget())) continue;
            if (this.reinforcementTypes != null) {
                boolean bl = false;
                for (Class<?> clazz : this.reinforcementTypes) {
                    if (mobEntity.getClass() != clazz) continue;
                    bl = true;
                    break;
                }
                if (bl) continue;
            }
            this.setAttackTarget(mobEntity, this.goalOwner.getRevengeTarget());
        }
        return;
    }

    protected void setAttackTarget(MobEntity mobEntity, LivingEntity livingEntity) {
        mobEntity.setAttackTarget(livingEntity);
    }
}

