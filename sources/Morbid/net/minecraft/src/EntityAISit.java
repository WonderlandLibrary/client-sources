package net.minecraft.src;

public class EntityAISit extends EntityAIBase
{
    private EntityTameable theEntity;
    private boolean isSitting;
    
    public EntityAISit(final EntityTameable par1EntityTameable) {
        this.isSitting = false;
        this.theEntity = par1EntityTameable;
        this.setMutexBits(5);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isTamed()) {
            return false;
        }
        if (this.theEntity.isInWater()) {
            return false;
        }
        if (!this.theEntity.onGround) {
            return false;
        }
        final EntityLiving var1 = this.theEntity.getOwner();
        return var1 == null || ((this.theEntity.getDistanceSqToEntity(var1) >= 144.0 || var1.getAITarget() == null) && this.isSitting);
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity.setSitting(true);
    }
    
    @Override
    public void resetTask() {
        this.theEntity.setSitting(false);
    }
    
    public void setSitting(final boolean par1) {
        this.isSitting = par1;
    }
}
