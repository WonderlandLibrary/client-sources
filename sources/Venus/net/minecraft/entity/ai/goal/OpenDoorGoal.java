/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.InteractDoorGoal;

public class OpenDoorGoal
extends InteractDoorGoal {
    private final boolean closeDoor;
    private int closeDoorTemporisation;

    public OpenDoorGoal(MobEntity mobEntity, boolean bl) {
        super(mobEntity);
        this.entity = mobEntity;
        this.closeDoor = bl;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.closeDoor && this.closeDoorTemporisation > 0 && super.shouldContinueExecuting();
    }

    @Override
    public void startExecuting() {
        this.closeDoorTemporisation = 20;
        this.toggleDoor(false);
    }

    @Override
    public void resetTask() {
        this.toggleDoor(true);
    }

    @Override
    public void tick() {
        --this.closeDoorTemporisation;
        super.tick();
    }
}

