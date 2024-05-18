package net.minecraft.src;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
    EntityTameable theEntityTameable;
    EntityLiving theTarget;
    
    public EntityAIOwnerHurtTarget(final EntityTameable par1EntityTameable) {
        super(par1EntityTameable, 32.0f, false);
        this.theEntityTameable = par1EntityTameable;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntityTameable.isTamed()) {
            return false;
        }
        final EntityLiving var1 = this.theEntityTameable.getOwner();
        if (var1 == null) {
            return false;
        }
        this.theTarget = var1.getLastAttackingEntity();
        return this.isSuitableTarget(this.theTarget, false);
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theTarget);
        super.startExecuting();
    }
}
