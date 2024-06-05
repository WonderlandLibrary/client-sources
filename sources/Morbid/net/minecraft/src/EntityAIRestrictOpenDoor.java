package net.minecraft.src;

public class EntityAIRestrictOpenDoor extends EntityAIBase
{
    private EntityCreature entityObj;
    private VillageDoorInfo frontDoor;
    
    public EntityAIRestrictOpenDoor(final EntityCreature par1EntityCreature) {
        this.entityObj = par1EntityCreature;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.entityObj.worldObj.isDaytime()) {
            return false;
        }
        final Village var1 = this.entityObj.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posY), MathHelper.floor_double(this.entityObj.posZ), 16);
        if (var1 == null) {
            return false;
        }
        this.frontDoor = var1.findNearestDoor(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posY), MathHelper.floor_double(this.entityObj.posZ));
        return this.frontDoor != null && this.frontDoor.getInsideDistanceSquare(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posY), MathHelper.floor_double(this.entityObj.posZ)) < 2.25;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entityObj.worldObj.isDaytime() && (!this.frontDoor.isDetachedFromVillageFlag && this.frontDoor.isInside(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posZ)));
    }
    
    @Override
    public void startExecuting() {
        this.entityObj.getNavigator().setBreakDoors(false);
        this.entityObj.getNavigator().setEnterDoors(false);
    }
    
    @Override
    public void resetTask() {
        this.entityObj.getNavigator().setBreakDoors(true);
        this.entityObj.getNavigator().setEnterDoors(true);
        this.frontDoor = null;
    }
    
    @Override
    public void updateTask() {
        this.frontDoor.incrementDoorOpeningRestrictionCounter();
    }
}
