/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.GroundPathHelper;

public class RestrictSunGoal
extends Goal {
    private final CreatureEntity entity;

    public RestrictSunGoal(CreatureEntity creatureEntity) {
        this.entity = creatureEntity;
    }

    @Override
    public boolean shouldExecute() {
        return this.entity.world.isDaytime() && this.entity.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty() && GroundPathHelper.isGroundNavigator(this.entity);
    }

    @Override
    public void startExecuting() {
        ((GroundPathNavigator)this.entity.getNavigator()).setAvoidSun(false);
    }

    @Override
    public void resetTask() {
        if (GroundPathHelper.isGroundNavigator(this.entity)) {
            ((GroundPathNavigator)this.entity.getNavigator()).setAvoidSun(true);
        }
    }
}

