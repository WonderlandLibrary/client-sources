/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClimberPathNavigator
extends GroundPathNavigator {
    private BlockPos targetPosition;

    public ClimberPathNavigator(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    public Path getPathToPos(BlockPos blockPos, int n) {
        this.targetPosition = blockPos;
        return super.getPathToPos(blockPos, n);
    }

    @Override
    public Path getPathToEntity(Entity entity2, int n) {
        this.targetPosition = entity2.getPosition();
        return super.getPathToEntity(entity2, n);
    }

    @Override
    public boolean tryMoveToEntityLiving(Entity entity2, double d) {
        Path path = this.getPathToEntity(entity2, 0);
        if (path != null) {
            return this.setPath(path, d);
        }
        this.targetPosition = entity2.getPosition();
        this.speed = d;
        return false;
    }

    @Override
    public void tick() {
        if (!this.noPath()) {
            super.tick();
        } else if (this.targetPosition != null) {
            if (!(this.targetPosition.withinDistance(this.entity.getPositionVec(), (double)this.entity.getWidth()) || this.entity.getPosY() > (double)this.targetPosition.getY() && new BlockPos((double)this.targetPosition.getX(), this.entity.getPosY(), (double)this.targetPosition.getZ()).withinDistance(this.entity.getPositionVec(), (double)this.entity.getWidth()))) {
                this.entity.getMoveHelper().setMoveTo(this.targetPosition.getX(), this.targetPosition.getY(), this.targetPosition.getZ(), this.speed);
            } else {
                this.targetPosition = null;
            }
        }
    }
}

