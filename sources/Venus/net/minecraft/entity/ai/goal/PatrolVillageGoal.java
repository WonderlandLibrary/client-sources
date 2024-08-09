/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class PatrolVillageGoal
extends RandomWalkingGoal {
    public PatrolVillageGoal(CreatureEntity creatureEntity, double d) {
        super(creatureEntity, d, 240, false);
    }

    @Override
    @Nullable
    protected Vector3d getPosition() {
        Vector3d vector3d;
        float f = this.creature.world.rand.nextFloat();
        if (this.creature.world.rand.nextFloat() < 0.3f) {
            return this.func_234031_j_();
        }
        if (f < 0.7f) {
            vector3d = this.func_234032_k_();
            if (vector3d == null) {
                vector3d = this.func_234033_l_();
            }
        } else {
            vector3d = this.func_234033_l_();
            if (vector3d == null) {
                vector3d = this.func_234032_k_();
            }
        }
        return vector3d == null ? this.func_234031_j_() : vector3d;
    }

    @Nullable
    private Vector3d func_234031_j_() {
        return RandomPositionGenerator.getLandPos(this.creature, 10, 7);
    }

    @Nullable
    private Vector3d func_234032_k_() {
        ServerWorld serverWorld = (ServerWorld)this.creature.world;
        List<VillagerEntity> list = serverWorld.getEntitiesWithinAABB(EntityType.VILLAGER, this.creature.getBoundingBox().grow(32.0), this::canSpawnGolems);
        if (list.isEmpty()) {
            return null;
        }
        VillagerEntity villagerEntity = list.get(this.creature.world.rand.nextInt(list.size()));
        Vector3d vector3d = villagerEntity.getPositionVec();
        return RandomPositionGenerator.func_234133_a_(this.creature, 10, 7, vector3d);
    }

    @Nullable
    private Vector3d func_234033_l_() {
        SectionPos sectionPos = this.func_234034_m_();
        if (sectionPos == null) {
            return null;
        }
        BlockPos blockPos = this.func_234029_a_(sectionPos);
        return blockPos == null ? null : RandomPositionGenerator.func_234133_a_(this.creature, 10, 7, Vector3d.copyCenteredHorizontally(blockPos));
    }

    @Nullable
    private SectionPos func_234034_m_() {
        ServerWorld serverWorld = (ServerWorld)this.creature.world;
        List list = SectionPos.getAllInBox(SectionPos.from(this.creature), 2).filter(arg_0 -> PatrolVillageGoal.lambda$func_234034_m_$0(serverWorld, arg_0)).collect(Collectors.toList());
        return list.isEmpty() ? null : (SectionPos)list.get(serverWorld.rand.nextInt(list.size()));
    }

    @Nullable
    private BlockPos func_234029_a_(SectionPos sectionPos) {
        ServerWorld serverWorld = (ServerWorld)this.creature.world;
        PointOfInterestManager pointOfInterestManager = serverWorld.getPointOfInterestManager();
        List list = pointOfInterestManager.func_219146_b(PatrolVillageGoal::lambda$func_234029_a_$1, sectionPos.getCenter(), 8, PointOfInterestManager.Status.IS_OCCUPIED).map(PointOfInterest::getPos).collect(Collectors.toList());
        return list.isEmpty() ? null : (BlockPos)list.get(serverWorld.rand.nextInt(list.size()));
    }

    private boolean canSpawnGolems(VillagerEntity villagerEntity) {
        return villagerEntity.canSpawnGolems(this.creature.world.getGameTime());
    }

    private static boolean lambda$func_234029_a_$1(PointOfInterestType pointOfInterestType) {
        return false;
    }

    private static boolean lambda$func_234034_m_$0(ServerWorld serverWorld, SectionPos sectionPos) {
        return serverWorld.sectionsToVillage(sectionPos) == 0;
    }
}

