/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.util.math.vector.Vector3d;

public class LlamaFollowCaravanGoal
extends Goal {
    public final LlamaEntity llama;
    private double speedModifier;
    private int distCheckCounter;

    public LlamaFollowCaravanGoal(LlamaEntity llamaEntity, double d) {
        this.llama = llamaEntity;
        this.speedModifier = d;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (!this.llama.getLeashed() && !this.llama.inCaravan()) {
            double d;
            LlamaEntity llamaEntity;
            List<Entity> list = this.llama.world.getEntitiesInAABBexcluding(this.llama, this.llama.getBoundingBox().grow(9.0, 4.0, 9.0), LlamaFollowCaravanGoal::lambda$shouldExecute$0);
            MobEntity mobEntity = null;
            double d2 = Double.MAX_VALUE;
            for (Entity entity2 : list) {
                llamaEntity = (LlamaEntity)entity2;
                if (!llamaEntity.inCaravan() || llamaEntity.hasCaravanTrail() || (d = this.llama.getDistanceSq(llamaEntity)) > d2) continue;
                d2 = d;
                mobEntity = llamaEntity;
            }
            if (mobEntity == null) {
                for (Entity entity2 : list) {
                    llamaEntity = (LlamaEntity)entity2;
                    if (!llamaEntity.getLeashed() || llamaEntity.hasCaravanTrail() || (d = this.llama.getDistanceSq(llamaEntity)) > d2) continue;
                    d2 = d;
                    mobEntity = llamaEntity;
                }
            }
            if (mobEntity == null) {
                return true;
            }
            if (d2 < 4.0) {
                return true;
            }
            if (!mobEntity.getLeashed() && !this.firstIsLeashed((LlamaEntity)mobEntity, 0)) {
                return true;
            }
            this.llama.joinCaravan((LlamaEntity)mobEntity);
            return false;
        }
        return true;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.llama.inCaravan() && this.llama.getCaravanHead().isAlive() && this.firstIsLeashed(this.llama, 1)) {
            double d = this.llama.getDistanceSq(this.llama.getCaravanHead());
            if (d > 676.0) {
                if (this.speedModifier <= 3.0) {
                    this.speedModifier *= 1.2;
                    this.distCheckCounter = 40;
                    return false;
                }
                if (this.distCheckCounter == 0) {
                    return true;
                }
            }
            if (this.distCheckCounter > 0) {
                --this.distCheckCounter;
            }
            return false;
        }
        return true;
    }

    @Override
    public void resetTask() {
        this.llama.leaveCaravan();
        this.speedModifier = 2.1;
    }

    @Override
    public void tick() {
        if (this.llama.inCaravan() && !(this.llama.getLeashHolder() instanceof LeashKnotEntity)) {
            LlamaEntity llamaEntity = this.llama.getCaravanHead();
            double d = this.llama.getDistance(llamaEntity);
            float f = 2.0f;
            Vector3d vector3d = new Vector3d(llamaEntity.getPosX() - this.llama.getPosX(), llamaEntity.getPosY() - this.llama.getPosY(), llamaEntity.getPosZ() - this.llama.getPosZ()).normalize().scale(Math.max(d - 2.0, 0.0));
            this.llama.getNavigator().tryMoveToXYZ(this.llama.getPosX() + vector3d.x, this.llama.getPosY() + vector3d.y, this.llama.getPosZ() + vector3d.z, this.speedModifier);
        }
    }

    private boolean firstIsLeashed(LlamaEntity llamaEntity, int n) {
        if (n > 8) {
            return true;
        }
        if (llamaEntity.inCaravan()) {
            if (llamaEntity.getCaravanHead().getLeashed()) {
                return false;
            }
            LlamaEntity llamaEntity2 = llamaEntity.getCaravanHead();
            return this.firstIsLeashed(llamaEntity2, ++n);
        }
        return true;
    }

    private static boolean lambda$shouldExecute$0(Entity entity2) {
        EntityType<?> entityType = entity2.getType();
        return entityType == EntityType.LLAMA || entityType == EntityType.TRADER_LLAMA;
    }
}

