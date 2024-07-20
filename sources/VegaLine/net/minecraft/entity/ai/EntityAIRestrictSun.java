/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAIRestrictSun
extends EntityAIBase {
    private final EntityCreature theEntity;

    public EntityAIRestrictSun(EntityCreature creature) {
        this.theEntity = creature;
    }

    @Override
    public boolean shouldExecute() {
        return this.theEntity.world.isDaytime() && this.theEntity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).func_190926_b();
    }

    @Override
    public void startExecuting() {
        ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(true);
    }

    @Override
    public void resetTask() {
        ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(false);
    }
}

