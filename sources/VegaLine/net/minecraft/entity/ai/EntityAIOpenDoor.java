/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;

public class EntityAIOpenDoor
extends EntityAIDoorInteract {
    boolean closeDoor;
    int closeDoorTemporisation;

    public EntityAIOpenDoor(EntityLiving entitylivingIn, boolean shouldClose) {
        super(entitylivingIn);
        this.theEntity = entitylivingIn;
        this.closeDoor = shouldClose;
    }

    @Override
    public boolean continueExecuting() {
        return this.closeDoor && this.closeDoorTemporisation > 0 && super.continueExecuting();
    }

    @Override
    public void startExecuting() {
        this.closeDoorTemporisation = 20;
        this.doorBlock.toggleDoor(this.theEntity.world, this.doorPosition, true);
    }

    @Override
    public void resetTask() {
        if (this.closeDoor) {
            this.doorBlock.toggleDoor(this.theEntity.world, this.doorPosition, false);
        }
    }

    @Override
    public void updateTask() {
        --this.closeDoorTemporisation;
        super.updateTask();
    }
}

