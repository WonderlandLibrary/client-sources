/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class RandomSwimmingGoal
extends RandomWalkingGoal {
    public RandomSwimmingGoal(CreatureEntity creatureEntity, double d, int n) {
        super(creatureEntity, d, n);
    }

    @Override
    @Nullable
    protected Vector3d getPosition() {
        Vector3d vector3d = RandomPositionGenerator.findRandomTarget(this.creature, 10, 7);
        int n = 0;
        while (vector3d != null && !this.creature.world.getBlockState(new BlockPos(vector3d)).allowsMovement(this.creature.world, new BlockPos(vector3d), PathType.WATER) && n++ < 10) {
            vector3d = RandomPositionGenerator.findRandomTarget(this.creature, 10, 7);
        }
        return vector3d;
    }
}

