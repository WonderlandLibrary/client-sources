/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.Vec3d;

public class EntityAIPlay
extends EntityAIBase {
    private final EntityVillager villagerObj;
    private EntityLivingBase targetVillager;
    private final double speed;
    private int playTime;

    public EntityAIPlay(EntityVillager villagerObjIn, double speedIn) {
        this.villagerObj = villagerObjIn;
        this.speed = speedIn;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Vec3d vec3d;
        if (this.villagerObj.getGrowingAge() >= 0) {
            return false;
        }
        if (this.villagerObj.getRNG().nextInt(400) != 0) {
            return false;
        }
        List<EntityVillager> list = this.villagerObj.world.getEntitiesWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(6.0, 3.0, 6.0));
        double d0 = Double.MAX_VALUE;
        for (EntityVillager entityvillager : list) {
            double d1;
            if (entityvillager == this.villagerObj || entityvillager.isPlaying() || entityvillager.getGrowingAge() >= 0 || !((d1 = entityvillager.getDistanceSqToEntity(this.villagerObj)) <= d0)) continue;
            d0 = d1;
            this.targetVillager = entityvillager;
        }
        return this.targetVillager != null || (vec3d = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3)) != null;
    }

    @Override
    public boolean continueExecuting() {
        return this.playTime > 0;
    }

    @Override
    public void startExecuting() {
        if (this.targetVillager != null) {
            this.villagerObj.setPlaying(true);
        }
        this.playTime = 1000;
    }

    @Override
    public void resetTask() {
        this.villagerObj.setPlaying(false);
        this.targetVillager = null;
    }

    @Override
    public void updateTask() {
        --this.playTime;
        if (this.targetVillager != null) {
            if (this.villagerObj.getDistanceSqToEntity(this.targetVillager) > 4.0) {
                this.villagerObj.getNavigator().tryMoveToEntityLiving(this.targetVillager, this.speed);
            }
        } else if (this.villagerObj.getNavigator().noPath()) {
            Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3);
            if (vec3d == null) {
                return;
            }
            this.villagerObj.getNavigator().tryMoveToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, this.speed);
        }
    }
}

