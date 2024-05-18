package net.minecraft.src;

public class EntityAIMoveIndoors extends EntityAIBase
{
    private EntityCreature entityObj;
    private VillageDoorInfo doorInfo;
    private int insidePosX;
    private int insidePosZ;
    
    public EntityAIMoveIndoors(final EntityCreature par1EntityCreature) {
        this.insidePosX = -1;
        this.insidePosZ = -1;
        this.entityObj = par1EntityCreature;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if ((this.entityObj.worldObj.isDaytime() && !this.entityObj.worldObj.isRaining()) || this.entityObj.worldObj.provider.hasNoSky) {
            return false;
        }
        if (this.entityObj.getRNG().nextInt(50) != 0) {
            return false;
        }
        if (this.insidePosX != -1 && this.entityObj.getDistanceSq(this.insidePosX, this.entityObj.posY, this.insidePosZ) < 4.0) {
            return false;
        }
        final Village var1 = this.entityObj.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posY), MathHelper.floor_double(this.entityObj.posZ), 14);
        if (var1 == null) {
            return false;
        }
        this.doorInfo = var1.findNearestDoorUnrestricted(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posY), MathHelper.floor_double(this.entityObj.posZ));
        return this.doorInfo != null;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entityObj.getNavigator().noPath();
    }
    
    @Override
    public void startExecuting() {
        this.insidePosX = -1;
        if (this.entityObj.getDistanceSq(this.doorInfo.getInsidePosX(), this.doorInfo.posY, this.doorInfo.getInsidePosZ()) > 256.0) {
            final Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.entityObj, 14, 3, this.entityObj.worldObj.getWorldVec3Pool().getVecFromPool(this.doorInfo.getInsidePosX() + 0.5, this.doorInfo.getInsidePosY(), this.doorInfo.getInsidePosZ() + 0.5));
            if (var1 != null) {
                this.entityObj.getNavigator().tryMoveToXYZ(var1.xCoord, var1.yCoord, var1.zCoord, 0.3f);
            }
        }
        else {
            this.entityObj.getNavigator().tryMoveToXYZ(this.doorInfo.getInsidePosX() + 0.5, this.doorInfo.getInsidePosY(), this.doorInfo.getInsidePosZ() + 0.5, 0.3f);
        }
    }
    
    @Override
    public void resetTask() {
        this.insidePosX = this.doorInfo.getInsidePosX();
        this.insidePosZ = this.doorInfo.getInsidePosZ();
        this.doorInfo = null;
    }
}
