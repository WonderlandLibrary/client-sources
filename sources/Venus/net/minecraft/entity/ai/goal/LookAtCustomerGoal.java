/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;

public class LookAtCustomerGoal
extends LookAtGoal {
    private final AbstractVillagerEntity villager;

    public LookAtCustomerGoal(AbstractVillagerEntity abstractVillagerEntity) {
        super(abstractVillagerEntity, PlayerEntity.class, 8.0f);
        this.villager = abstractVillagerEntity;
    }

    @Override
    public boolean shouldExecute() {
        if (this.villager.hasCustomer()) {
            this.closestEntity = this.villager.getCustomer();
            return false;
        }
        return true;
    }
}

