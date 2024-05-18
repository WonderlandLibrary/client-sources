package net.minecraft.src;

public class EntityAIMoveTwardsRestriction extends EntityAIBase
{
    private EntityCreature theEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private float movementSpeed;
    
    public EntityAIMoveTwardsRestriction(final EntityCreature par1EntityCreature, final float par2) {
        this.theEntity = par1EntityCreature;
        this.movementSpeed = par2;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theEntity.isWithinHomeDistanceCurrentPosition()) {
            return false;
        }
        final ChunkCoordinates var1 = this.theEntity.getHomePosition();
        final Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(var1.posX, var1.posY, var1.posZ));
        if (var2 == null) {
            return false;
        }
        this.movePosX = var2.xCoord;
        this.movePosY = var2.yCoord;
        this.movePosZ = var2.zCoord;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.theEntity.getNavigator().noPath();
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
    }
}
