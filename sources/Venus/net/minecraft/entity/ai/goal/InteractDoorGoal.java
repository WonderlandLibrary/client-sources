/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.GroundPathHelper;
import net.minecraft.util.math.BlockPos;

public abstract class InteractDoorGoal
extends Goal {
    protected MobEntity entity;
    protected BlockPos doorPosition = BlockPos.ZERO;
    protected boolean doorInteract;
    private boolean hasStoppedDoorInteraction;
    private float entityPositionX;
    private float entityPositionZ;

    public InteractDoorGoal(MobEntity mobEntity) {
        this.entity = mobEntity;
        if (!GroundPathHelper.isGroundNavigator(mobEntity)) {
            throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
        }
    }

    protected boolean canDestroy() {
        if (!this.doorInteract) {
            return true;
        }
        BlockState blockState = this.entity.world.getBlockState(this.doorPosition);
        if (!(blockState.getBlock() instanceof DoorBlock)) {
            this.doorInteract = false;
            return true;
        }
        return blockState.get(DoorBlock.OPEN);
    }

    protected void toggleDoor(boolean bl) {
        BlockState blockState;
        if (this.doorInteract && (blockState = this.entity.world.getBlockState(this.doorPosition)).getBlock() instanceof DoorBlock) {
            ((DoorBlock)blockState.getBlock()).openDoor(this.entity.world, blockState, this.doorPosition, bl);
        }
    }

    @Override
    public boolean shouldExecute() {
        if (!GroundPathHelper.isGroundNavigator(this.entity)) {
            return true;
        }
        if (!this.entity.collidedHorizontally) {
            return true;
        }
        GroundPathNavigator groundPathNavigator = (GroundPathNavigator)this.entity.getNavigator();
        Path path = groundPathNavigator.getPath();
        if (path != null && !path.isFinished() && groundPathNavigator.getEnterDoors()) {
            for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i) {
                PathPoint pathPoint = path.getPathPointFromIndex(i);
                this.doorPosition = new BlockPos(pathPoint.x, pathPoint.y + 1, pathPoint.z);
                if (this.entity.getDistanceSq(this.doorPosition.getX(), this.entity.getPosY(), this.doorPosition.getZ()) > 2.25) continue;
                this.doorInteract = DoorBlock.isWooden(this.entity.world, this.doorPosition);
                if (!this.doorInteract) continue;
                return false;
            }
            this.doorPosition = this.entity.getPosition().up();
            this.doorInteract = DoorBlock.isWooden(this.entity.world, this.doorPosition);
            return this.doorInteract;
        }
        return true;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.hasStoppedDoorInteraction;
    }

    @Override
    public void startExecuting() {
        this.hasStoppedDoorInteraction = false;
        this.entityPositionX = (float)((double)this.doorPosition.getX() + 0.5 - this.entity.getPosX());
        this.entityPositionZ = (float)((double)this.doorPosition.getZ() + 0.5 - this.entity.getPosZ());
    }

    @Override
    public void tick() {
        float f;
        float f2 = (float)((double)this.doorPosition.getX() + 0.5 - this.entity.getPosX());
        float f3 = this.entityPositionX * f2 + this.entityPositionZ * (f = (float)((double)this.doorPosition.getZ() + 0.5 - this.entity.getPosZ()));
        if (f3 < 0.0f) {
            this.hasStoppedDoorInteraction = true;
        }
    }
}

