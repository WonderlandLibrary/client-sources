/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.util.math.vector.Vector3d;

public class WaterAvoidingRandomWalkingGoal
extends RandomWalkingGoal {
    protected final float probability;

    public WaterAvoidingRandomWalkingGoal(CreatureEntity creatureEntity, double d) {
        this(creatureEntity, d, 0.001f);
    }

    public WaterAvoidingRandomWalkingGoal(CreatureEntity creatureEntity, double d, float f) {
        super(creatureEntity, d);
        this.probability = f;
    }

    @Override
    @Nullable
    protected Vector3d getPosition() {
        if (this.creature.isInWaterOrBubbleColumn()) {
            Vector3d vector3d = RandomPositionGenerator.getLandPos(this.creature, 15, 7);
            return vector3d == null ? super.getPosition() : vector3d;
        }
        return this.creature.getRNG().nextFloat() >= this.probability ? RandomPositionGenerator.getLandPos(this.creature, 10, 7) : super.getPosition();
    }
}

