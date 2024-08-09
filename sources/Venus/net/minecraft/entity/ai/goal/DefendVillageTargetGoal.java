/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class DefendVillageTargetGoal
extends TargetGoal {
    private final IronGolemEntity irongolem;
    private LivingEntity villageAgressorTarget;
    private final EntityPredicate distancePredicate = new EntityPredicate().setDistance(64.0);

    public DefendVillageTargetGoal(IronGolemEntity ironGolemEntity) {
        super(ironGolemEntity, false, true);
        this.irongolem = ironGolemEntity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean shouldExecute() {
        AxisAlignedBB axisAlignedBB = this.irongolem.getBoundingBox().grow(10.0, 8.0, 10.0);
        List<VillagerEntity> list = this.irongolem.world.getTargettableEntitiesWithinAABB(VillagerEntity.class, this.distancePredicate, this.irongolem, axisAlignedBB);
        List<PlayerEntity> list2 = this.irongolem.world.getTargettablePlayersWithinAABB(this.distancePredicate, this.irongolem, axisAlignedBB);
        for (LivingEntity livingEntity : list) {
            VillagerEntity villagerEntity = (VillagerEntity)livingEntity;
            for (PlayerEntity playerEntity : list2) {
                int n = villagerEntity.getPlayerReputation(playerEntity);
                if (n > -100) continue;
                this.villageAgressorTarget = playerEntity;
            }
        }
        if (this.villageAgressorTarget == null) {
            return true;
        }
        return !(this.villageAgressorTarget instanceof PlayerEntity) || !this.villageAgressorTarget.isSpectator() && !((PlayerEntity)this.villageAgressorTarget).isCreative();
    }

    @Override
    public void startExecuting() {
        this.irongolem.setAttackTarget(this.villageAgressorTarget);
        super.startExecuting();
    }
}

