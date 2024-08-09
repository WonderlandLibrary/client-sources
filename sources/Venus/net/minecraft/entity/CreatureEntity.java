/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class CreatureEntity
extends MobEntity {
    protected CreatureEntity(EntityType<? extends CreatureEntity> entityType, World world) {
        super((EntityType<? extends MobEntity>)entityType, world);
    }

    public float getBlockPathWeight(BlockPos blockPos) {
        return this.getBlockPathWeight(blockPos, this.world);
    }

    public float getBlockPathWeight(BlockPos blockPos, IWorldReader iWorldReader) {
        return 0.0f;
    }

    @Override
    public boolean canSpawn(IWorld iWorld, SpawnReason spawnReason) {
        return this.getBlockPathWeight(this.getPosition(), iWorld) >= 0.0f;
    }

    public boolean hasPath() {
        return !this.getNavigator().noPath();
    }

    @Override
    protected void updateLeashedState() {
        super.updateLeashedState();
        Entity entity2 = this.getLeashHolder();
        if (entity2 != null && entity2.world == this.world) {
            this.setHomePosAndDistance(entity2.getPosition(), 5);
            float f = this.getDistance(entity2);
            if (this instanceof TameableEntity && ((TameableEntity)this).isSleeping()) {
                if (f > 10.0f) {
                    this.clearLeashed(true, false);
                }
                return;
            }
            this.onLeashDistance(f);
            if (f > 10.0f) {
                this.clearLeashed(true, false);
                this.goalSelector.disableFlag(Goal.Flag.MOVE);
            } else if (f > 6.0f) {
                double d = (entity2.getPosX() - this.getPosX()) / (double)f;
                double d2 = (entity2.getPosY() - this.getPosY()) / (double)f;
                double d3 = (entity2.getPosZ() - this.getPosZ()) / (double)f;
                this.setMotion(this.getMotion().add(Math.copySign(d * d * 0.4, d), Math.copySign(d2 * d2 * 0.4, d2), Math.copySign(d3 * d3 * 0.4, d3)));
            } else {
                this.goalSelector.enableFlag(Goal.Flag.MOVE);
                float f2 = 2.0f;
                Vector3d vector3d = new Vector3d(entity2.getPosX() - this.getPosX(), entity2.getPosY() - this.getPosY(), entity2.getPosZ() - this.getPosZ()).normalize().scale(Math.max(f - 2.0f, 0.0f));
                this.getNavigator().tryMoveToXYZ(this.getPosX() + vector3d.x, this.getPosY() + vector3d.y, this.getPosZ() + vector3d.z, this.followLeashSpeed());
            }
        }
    }

    protected double followLeashSpeed() {
        return 1.0;
    }

    protected void onLeashDistance(float f) {
    }
}

