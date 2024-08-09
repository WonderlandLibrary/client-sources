/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class ReturnToVillageGoal
extends RandomWalkingGoal {
    public ReturnToVillageGoal(CreatureEntity creatureEntity, double d, boolean bl) {
        super(creatureEntity, d, 10, bl);
    }

    @Override
    public boolean shouldExecute() {
        ServerWorld serverWorld = (ServerWorld)this.creature.world;
        BlockPos blockPos = this.creature.getPosition();
        return serverWorld.isVillage(blockPos) ? false : super.shouldExecute();
    }

    @Override
    @Nullable
    protected Vector3d getPosition() {
        ServerWorld serverWorld = (ServerWorld)this.creature.world;
        BlockPos blockPos = this.creature.getPosition();
        SectionPos sectionPos = SectionPos.from(blockPos);
        SectionPos sectionPos2 = BrainUtil.getClosestVillageSection(serverWorld, sectionPos, 2);
        return sectionPos2 != sectionPos ? RandomPositionGenerator.findRandomTargetBlockTowards(this.creature, 10, 7, Vector3d.copyCenteredHorizontally(sectionPos2.getCenter())) : null;
    }
}

