/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;

public class FindWaterGoal
extends Goal {
    private final CreatureEntity creature;

    public FindWaterGoal(CreatureEntity creatureEntity) {
        this.creature = creatureEntity;
    }

    @Override
    public boolean shouldExecute() {
        return this.creature.isOnGround() && !this.creature.world.getFluidState(this.creature.getPosition()).isTagged(FluidTags.WATER);
    }

    @Override
    public void startExecuting() {
        Vector3i vector3i = null;
        for (BlockPos blockPos : BlockPos.getAllInBoxMutable(MathHelper.floor(this.creature.getPosX() - 2.0), MathHelper.floor(this.creature.getPosY() - 2.0), MathHelper.floor(this.creature.getPosZ() - 2.0), MathHelper.floor(this.creature.getPosX() + 2.0), MathHelper.floor(this.creature.getPosY()), MathHelper.floor(this.creature.getPosZ() + 2.0))) {
            if (!this.creature.world.getFluidState(blockPos).isTagged(FluidTags.WATER)) continue;
            vector3i = blockPos;
            break;
        }
        if (vector3i != null) {
            this.creature.getMoveHelper().setMoveTo(vector3i.getX(), vector3i.getY(), vector3i.getZ(), 1.0);
        }
    }
}

