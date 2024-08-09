/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;

public class SwimGoal
extends Goal {
    private final MobEntity entity;

    public SwimGoal(MobEntity mobEntity) {
        this.entity = mobEntity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP));
        mobEntity.getNavigator().setCanSwim(false);
    }

    @Override
    public boolean shouldExecute() {
        return this.entity.isInWater() && this.entity.func_233571_b_(FluidTags.WATER) > this.entity.func_233579_cu_() || this.entity.isInLava();
    }

    @Override
    public void tick() {
        if (this.entity.getRNG().nextFloat() < 0.8f) {
            this.entity.getJumpController().setJumping();
        }
    }
}

