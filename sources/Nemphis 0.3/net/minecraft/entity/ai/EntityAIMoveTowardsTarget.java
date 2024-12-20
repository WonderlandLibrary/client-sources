/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

public class EntityAIMoveTowardsTarget
extends EntityAIBase {
    private EntityCreature theEntity;
    private EntityLivingBase targetEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private double speed;
    private float maxTargetDistance;
    private static final String __OBFID = "CL_00001599";

    public EntityAIMoveTowardsTarget(EntityCreature p_i1640_1_, double p_i1640_2_, float p_i1640_4_) {
        this.theEntity = p_i1640_1_;
        this.speed = p_i1640_2_;
        this.maxTargetDistance = p_i1640_4_;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        this.targetEntity = this.theEntity.getAttackTarget();
        if (this.targetEntity == null) {
            return false;
        }
        if (this.targetEntity.getDistanceSqToEntity(this.theEntity) > (double)(this.maxTargetDistance * this.maxTargetDistance)) {
            return false;
        }
        Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, new Vec3(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ));
        if (var1 == null) {
            return false;
        }
        this.movePosX = var1.xCoord;
        this.movePosY = var1.yCoord;
        this.movePosZ = var1.zCoord;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        if (!this.theEntity.getNavigator().noPath() && this.targetEntity.isEntityAlive() && this.targetEntity.getDistanceSqToEntity(this.theEntity) < (double)(this.maxTargetDistance * this.maxTargetDistance)) {
            return true;
        }
        return false;
    }

    @Override
    public void resetTask() {
        this.targetEntity = null;
    }

    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.speed);
    }
}

