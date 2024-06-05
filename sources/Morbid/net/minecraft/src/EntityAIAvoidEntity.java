package net.minecraft.src;

import java.util.*;

public class EntityAIAvoidEntity extends EntityAIBase
{
    public final IEntitySelector field_98218_a;
    private EntityCreature theEntity;
    private float farSpeed;
    private float nearSpeed;
    private Entity closestLivingEntity;
    private float distanceFromEntity;
    private PathEntity entityPathEntity;
    private PathNavigate entityPathNavigate;
    private Class targetEntityClass;
    
    public EntityAIAvoidEntity(final EntityCreature par1EntityCreature, final Class par2Class, final float par3, final float par4, final float par5) {
        this.field_98218_a = new EntityAIAvoidEntitySelector(this);
        this.theEntity = par1EntityCreature;
        this.targetEntityClass = par2Class;
        this.distanceFromEntity = par3;
        this.farSpeed = par4;
        this.nearSpeed = par5;
        this.entityPathNavigate = par1EntityCreature.getNavigator();
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.targetEntityClass == EntityPlayer.class) {
            if (this.theEntity instanceof EntityTameable && ((EntityTameable)this.theEntity).isTamed()) {
                return false;
            }
            this.closestLivingEntity = this.theEntity.worldObj.getClosestPlayerToEntity(this.theEntity, this.distanceFromEntity);
            if (this.closestLivingEntity == null) {
                return false;
            }
        }
        else {
            final List var1 = this.theEntity.worldObj.selectEntitiesWithinAABB(this.targetEntityClass, this.theEntity.boundingBox.expand(this.distanceFromEntity, 3.0, this.distanceFromEntity), this.field_98218_a);
            if (var1.isEmpty()) {
                return false;
            }
            this.closestLivingEntity = var1.get(0);
        }
        final Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
        if (var2 == null) {
            return false;
        }
        if (this.closestLivingEntity.getDistanceSq(var2.xCoord, var2.yCoord, var2.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
            return false;
        }
        this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(var2.xCoord, var2.yCoord, var2.zCoord);
        return this.entityPathEntity != null && this.entityPathEntity.isDestinationSame(var2);
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entityPathNavigate.noPath();
    }
    
    @Override
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
    }
    
    @Override
    public void resetTask() {
        this.closestLivingEntity = null;
    }
    
    @Override
    public void updateTask() {
        if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0) {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
        }
        else {
            this.theEntity.getNavigator().setSpeed(this.farSpeed);
        }
    }
    
    static EntityCreature func_98217_a(final EntityAIAvoidEntity par0EntityAIAvoidEntity) {
        return par0EntityAIAvoidEntity.theEntity;
    }
}
