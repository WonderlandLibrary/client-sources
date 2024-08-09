/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class LandOnOwnersShoulderGoal
extends Goal {
    private final ShoulderRidingEntity entity;
    private ServerPlayerEntity owner;
    private boolean isSittingOnShoulder;

    public LandOnOwnersShoulderGoal(ShoulderRidingEntity shoulderRidingEntity) {
        this.entity = shoulderRidingEntity;
    }

    @Override
    public boolean shouldExecute() {
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)this.entity.getOwner();
        boolean bl = serverPlayerEntity != null && !serverPlayerEntity.isSpectator() && !serverPlayerEntity.abilities.isFlying && !serverPlayerEntity.isInWater();
        return !this.entity.isSitting() && bl && this.entity.canSitOnShoulder();
    }

    @Override
    public boolean isPreemptible() {
        return !this.isSittingOnShoulder;
    }

    @Override
    public void startExecuting() {
        this.owner = (ServerPlayerEntity)this.entity.getOwner();
        this.isSittingOnShoulder = false;
    }

    @Override
    public void tick() {
        if (!this.isSittingOnShoulder && !this.entity.isSleeping() && !this.entity.getLeashed() && this.entity.getBoundingBox().intersects(this.owner.getBoundingBox())) {
            this.isSittingOnShoulder = this.entity.func_213439_d(this.owner);
        }
    }
}

