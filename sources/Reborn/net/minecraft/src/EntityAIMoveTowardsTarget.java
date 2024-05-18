package net.minecraft.src;

public class EntityAIMoveTowardsTarget extends EntityAIBase
{
    private EntityCreature theEntity;
    private EntityLiving targetEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private float field_75425_f;
    private float field_75426_g;
    
    public EntityAIMoveTowardsTarget(final EntityCreature par1EntityCreature, final float par2, final float par3) {
        this.theEntity = par1EntityCreature;
        this.field_75425_f = par2;
        this.field_75426_g = par3;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        this.targetEntity = this.theEntity.getAttackTarget();
        if (this.targetEntity == null) {
            return false;
        }
        if (this.targetEntity.getDistanceSqToEntity(this.theEntity) > this.field_75426_g * this.field_75426_g) {
            return false;
        }
        final Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ));
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
        return !this.theEntity.getNavigator().noPath() && this.targetEntity.isEntityAlive() && this.targetEntity.getDistanceSqToEntity(this.theEntity) < this.field_75426_g * this.field_75426_g;
    }
    
    @Override
    public void resetTask() {
        this.targetEntity = null;
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.field_75425_f);
    }
}
