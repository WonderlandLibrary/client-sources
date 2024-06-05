package net.minecraft.src;

public abstract class EntityAIDoorInteract extends EntityAIBase
{
    protected EntityLiving theEntity;
    protected int entityPosX;
    protected int entityPosY;
    protected int entityPosZ;
    protected BlockDoor targetDoor;
    boolean hasStoppedDoorInteraction;
    float entityPositionX;
    float entityPositionZ;
    
    public EntityAIDoorInteract(final EntityLiving par1EntityLiving) {
        this.theEntity = par1EntityLiving;
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isCollidedHorizontally) {
            return false;
        }
        final PathNavigate var1 = this.theEntity.getNavigator();
        final PathEntity var2 = var1.getPath();
        if (var2 != null && !var2.isFinished() && var1.getCanBreakDoors()) {
            for (int var3 = 0; var3 < Math.min(var2.getCurrentPathIndex() + 2, var2.getCurrentPathLength()); ++var3) {
                final PathPoint var4 = var2.getPathPointFromIndex(var3);
                this.entityPosX = var4.xCoord;
                this.entityPosY = var4.yCoord + 1;
                this.entityPosZ = var4.zCoord;
                if (this.theEntity.getDistanceSq(this.entityPosX, this.theEntity.posY, this.entityPosZ) <= 2.25) {
                    this.targetDoor = this.findUsableDoor(this.entityPosX, this.entityPosY, this.entityPosZ);
                    if (this.targetDoor != null) {
                        return true;
                    }
                }
            }
            this.entityPosX = MathHelper.floor_double(this.theEntity.posX);
            this.entityPosY = MathHelper.floor_double(this.theEntity.posY + 1.0);
            this.entityPosZ = MathHelper.floor_double(this.theEntity.posZ);
            this.targetDoor = this.findUsableDoor(this.entityPosX, this.entityPosY, this.entityPosZ);
            return this.targetDoor != null;
        }
        return false;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.hasStoppedDoorInteraction;
    }
    
    @Override
    public void startExecuting() {
        this.hasStoppedDoorInteraction = false;
        this.entityPositionX = (float)(this.entityPosX + 0.5f - this.theEntity.posX);
        this.entityPositionZ = (float)(this.entityPosZ + 0.5f - this.theEntity.posZ);
    }
    
    @Override
    public void updateTask() {
        final float var1 = (float)(this.entityPosX + 0.5f - this.theEntity.posX);
        final float var2 = (float)(this.entityPosZ + 0.5f - this.theEntity.posZ);
        final float var3 = this.entityPositionX * var1 + this.entityPositionZ * var2;
        if (var3 < 0.0f) {
            this.hasStoppedDoorInteraction = true;
        }
    }
    
    private BlockDoor findUsableDoor(final int par1, final int par2, final int par3) {
        final int var4 = this.theEntity.worldObj.getBlockId(par1, par2, par3);
        return (var4 != Block.doorWood.blockID) ? null : ((BlockDoor)Block.blocksList[var4]);
    }
}
