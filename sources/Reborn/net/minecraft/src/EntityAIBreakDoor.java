package net.minecraft.src;

public class EntityAIBreakDoor extends EntityAIDoorInteract
{
    private int breakingTime;
    private int field_75358_j;
    
    public EntityAIBreakDoor(final EntityLiving par1EntityLiving) {
        super(par1EntityLiving);
        this.field_75358_j = -1;
    }
    
    @Override
    public boolean shouldExecute() {
        return super.shouldExecute() && this.theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") && !this.targetDoor.isDoorOpen(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ);
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        this.breakingTime = 0;
    }
    
    @Override
    public boolean continueExecuting() {
        final double var1 = this.theEntity.getDistanceSq(this.entityPosX, this.entityPosY, this.entityPosZ);
        return this.breakingTime <= 240 && !this.targetDoor.isDoorOpen(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ) && var1 < 4.0;
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
        this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.entityId, this.entityPosX, this.entityPosY, this.entityPosZ, -1);
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        if (this.theEntity.getRNG().nextInt(20) == 0) {
            this.theEntity.worldObj.playAuxSFX(1010, this.entityPosX, this.entityPosY, this.entityPosZ, 0);
        }
        ++this.breakingTime;
        final int var1 = (int)(this.breakingTime / 240.0f * 10.0f);
        if (var1 != this.field_75358_j) {
            this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.entityId, this.entityPosX, this.entityPosY, this.entityPosZ, var1);
            this.field_75358_j = var1;
        }
        if (this.breakingTime == 240 && this.theEntity.worldObj.difficultySetting == 3) {
            this.theEntity.worldObj.setBlockToAir(this.entityPosX, this.entityPosY, this.entityPosZ);
            this.theEntity.worldObj.playAuxSFX(1012, this.entityPosX, this.entityPosY, this.entityPosZ, 0);
            this.theEntity.worldObj.playAuxSFX(2001, this.entityPosX, this.entityPosY, this.entityPosZ, this.targetDoor.blockID);
        }
    }
}
