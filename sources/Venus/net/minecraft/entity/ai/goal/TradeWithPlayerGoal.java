/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;

public class TradeWithPlayerGoal
extends Goal {
    private final AbstractVillagerEntity villager;

    public TradeWithPlayerGoal(AbstractVillagerEntity abstractVillagerEntity) {
        this.villager = abstractVillagerEntity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (!this.villager.isAlive()) {
            return true;
        }
        if (this.villager.isInWater()) {
            return true;
        }
        if (!this.villager.isOnGround()) {
            return true;
        }
        if (this.villager.velocityChanged) {
            return true;
        }
        PlayerEntity playerEntity = this.villager.getCustomer();
        if (playerEntity == null) {
            return true;
        }
        if (this.villager.getDistanceSq(playerEntity) > 16.0) {
            return true;
        }
        return playerEntity.openContainer != null;
    }

    @Override
    public void startExecuting() {
        this.villager.getNavigator().clearPath();
    }

    @Override
    public void resetTask() {
        this.villager.setCustomer(null);
    }
}

