/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class MoveThroughVillageAtNightGoal
extends Goal {
    private final CreatureEntity entity;
    private final int field_220757_b;
    @Nullable
    private BlockPos field_220758_c;

    public MoveThroughVillageAtNightGoal(CreatureEntity creatureEntity, int n) {
        this.entity = creatureEntity;
        this.field_220757_b = n;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (this.entity.isBeingRidden()) {
            return true;
        }
        if (this.entity.world.isDaytime()) {
            return true;
        }
        if (this.entity.getRNG().nextInt(this.field_220757_b) != 0) {
            return true;
        }
        ServerWorld serverWorld = (ServerWorld)this.entity.world;
        BlockPos blockPos = this.entity.getPosition();
        if (!serverWorld.func_241119_a_(blockPos, 1)) {
            return true;
        }
        Vector3d vector3d = RandomPositionGenerator.func_221024_a(this.entity, 15, 7, arg_0 -> MoveThroughVillageAtNightGoal.lambda$shouldExecute$0(serverWorld, arg_0));
        this.field_220758_c = vector3d == null ? null : new BlockPos(vector3d);
        return this.field_220758_c != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.field_220758_c != null && !this.entity.getNavigator().noPath() && this.entity.getNavigator().getTargetPos().equals(this.field_220758_c);
    }

    @Override
    public void tick() {
        PathNavigator pathNavigator;
        if (this.field_220758_c != null && (pathNavigator = this.entity.getNavigator()).noPath() && !this.field_220758_c.withinDistance(this.entity.getPositionVec(), 10.0)) {
            Vector3d vector3d = Vector3d.copyCenteredHorizontally(this.field_220758_c);
            Vector3d vector3d2 = this.entity.getPositionVec();
            Vector3d vector3d3 = vector3d2.subtract(vector3d);
            vector3d = vector3d3.scale(0.4).add(vector3d);
            Vector3d vector3d4 = vector3d.subtract(vector3d2).normalize().scale(10.0).add(vector3d2);
            BlockPos blockPos = new BlockPos(vector3d4);
            if (!pathNavigator.tryMoveToXYZ((blockPos = this.entity.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockPos)).getX(), blockPos.getY(), blockPos.getZ(), 1.0)) {
                this.func_220754_g();
            }
        }
    }

    private void func_220754_g() {
        Random random2 = this.entity.getRNG();
        BlockPos blockPos = this.entity.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, this.entity.getPosition().add(-8 + random2.nextInt(16), 0, -8 + random2.nextInt(16)));
        this.entity.getNavigator().tryMoveToXYZ(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0);
    }

    private static double lambda$shouldExecute$0(ServerWorld serverWorld, BlockPos blockPos) {
        return -serverWorld.sectionsToVillage(SectionPos.from(blockPos));
    }
}

