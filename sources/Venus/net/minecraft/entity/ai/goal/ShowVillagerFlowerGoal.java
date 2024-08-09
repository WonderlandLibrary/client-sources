/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;

public class ShowVillagerFlowerGoal
extends Goal {
    private static final EntityPredicate field_220738_a = new EntityPredicate().setDistance(6.0).allowFriendlyFire().allowInvulnerable();
    private final IronGolemEntity ironGolem;
    private VillagerEntity villager;
    private int lookTime;

    public ShowVillagerFlowerGoal(IronGolemEntity ironGolemEntity) {
        this.ironGolem = ironGolemEntity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        if (!this.ironGolem.world.isDaytime()) {
            return true;
        }
        if (this.ironGolem.getRNG().nextInt(8000) != 0) {
            return true;
        }
        this.villager = this.ironGolem.world.getClosestEntityWithinAABB(VillagerEntity.class, field_220738_a, this.ironGolem, this.ironGolem.getPosX(), this.ironGolem.getPosY(), this.ironGolem.getPosZ(), this.ironGolem.getBoundingBox().grow(6.0, 2.0, 6.0));
        return this.villager != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.lookTime > 0;
    }

    @Override
    public void startExecuting() {
        this.lookTime = 400;
        this.ironGolem.setHoldingRose(false);
    }

    @Override
    public void resetTask() {
        this.ironGolem.setHoldingRose(true);
        this.villager = null;
    }

    @Override
    public void tick() {
        this.ironGolem.getLookController().setLookPositionWithEntity(this.villager, 30.0f, 30.0f);
        --this.lookTime;
    }
}

