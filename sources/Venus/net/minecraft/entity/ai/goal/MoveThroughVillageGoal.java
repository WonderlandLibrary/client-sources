/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.GroundPathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class MoveThroughVillageGoal
extends Goal {
    protected final CreatureEntity entity;
    private final double movementSpeed;
    private Path path;
    private BlockPos field_220735_d;
    private final boolean isNocturnal;
    private final List<BlockPos> doorList = Lists.newArrayList();
    private final int maxDistance;
    private final BooleanSupplier booleanSupplier;

    public MoveThroughVillageGoal(CreatureEntity creatureEntity, double d, boolean bl, int n, BooleanSupplier booleanSupplier) {
        this.entity = creatureEntity;
        this.movementSpeed = d;
        this.isNocturnal = bl;
        this.maxDistance = n;
        this.booleanSupplier = booleanSupplier;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        if (!GroundPathHelper.isGroundNavigator(creatureEntity)) {
            throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
        }
    }

    @Override
    public boolean shouldExecute() {
        if (!GroundPathHelper.isGroundNavigator(this.entity)) {
            return true;
        }
        this.resizeDoorList();
        if (this.isNocturnal && this.entity.world.isDaytime()) {
            return true;
        }
        ServerWorld serverWorld = (ServerWorld)this.entity.world;
        BlockPos blockPos = this.entity.getPosition();
        if (!serverWorld.func_241119_a_(blockPos, 1)) {
            return true;
        }
        Vector3d vector3d = RandomPositionGenerator.func_221024_a(this.entity, 15, 7, arg_0 -> this.lambda$shouldExecute$0(serverWorld, blockPos, arg_0));
        if (vector3d == null) {
            return true;
        }
        Optional<BlockPos> optional = serverWorld.getPointOfInterestManager().find(PointOfInterestType.MATCH_ANY, this::func_220733_a, new BlockPos(vector3d), 10, PointOfInterestManager.Status.IS_OCCUPIED);
        if (!optional.isPresent()) {
            return true;
        }
        this.field_220735_d = optional.get().toImmutable();
        GroundPathNavigator groundPathNavigator = (GroundPathNavigator)this.entity.getNavigator();
        boolean bl = groundPathNavigator.getEnterDoors();
        groundPathNavigator.setBreakDoors(this.booleanSupplier.getAsBoolean());
        this.path = groundPathNavigator.getPathToPos(this.field_220735_d, 0);
        groundPathNavigator.setBreakDoors(bl);
        if (this.path == null) {
            Vector3d vector3d2 = RandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 10, 7, Vector3d.copyCenteredHorizontally(this.field_220735_d));
            if (vector3d2 == null) {
                return true;
            }
            groundPathNavigator.setBreakDoors(this.booleanSupplier.getAsBoolean());
            this.path = this.entity.getNavigator().getPathToPos(vector3d2.x, vector3d2.y, vector3d2.z, 0);
            groundPathNavigator.setBreakDoors(bl);
            if (this.path == null) {
                return true;
            }
        }
        for (int i = 0; i < this.path.getCurrentPathLength(); ++i) {
            PathPoint pathPoint = this.path.getPathPointFromIndex(i);
            BlockPos blockPos2 = new BlockPos(pathPoint.x, pathPoint.y + 1, pathPoint.z);
            if (!DoorBlock.isWooden(this.entity.world, blockPos2)) continue;
            this.path = this.entity.getNavigator().getPathToPos(pathPoint.x, pathPoint.y, pathPoint.z, 0);
            break;
        }
        return this.path != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.entity.getNavigator().noPath()) {
            return true;
        }
        return !this.field_220735_d.withinDistance(this.entity.getPositionVec(), (double)(this.entity.getWidth() + (float)this.maxDistance));
    }

    @Override
    public void startExecuting() {
        this.entity.getNavigator().setPath(this.path, this.movementSpeed);
    }

    @Override
    public void resetTask() {
        if (this.entity.getNavigator().noPath() || this.field_220735_d.withinDistance(this.entity.getPositionVec(), (double)this.maxDistance)) {
            this.doorList.add(this.field_220735_d);
        }
    }

    private boolean func_220733_a(BlockPos blockPos) {
        for (BlockPos blockPos2 : this.doorList) {
            if (!Objects.equals(blockPos, blockPos2)) continue;
            return true;
        }
        return false;
    }

    private void resizeDoorList() {
        if (this.doorList.size() > 15) {
            this.doorList.remove(0);
        }
    }

    private double lambda$shouldExecute$0(ServerWorld serverWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (!serverWorld.isVillage(blockPos2)) {
            return Double.NEGATIVE_INFINITY;
        }
        Optional<BlockPos> optional = serverWorld.getPointOfInterestManager().find(PointOfInterestType.MATCH_ANY, this::func_220733_a, blockPos2, 10, PointOfInterestManager.Status.IS_OCCUPIED);
        return !optional.isPresent() ? Double.NEGATIVE_INFINITY : -optional.get().distanceSq(blockPos);
    }
}

