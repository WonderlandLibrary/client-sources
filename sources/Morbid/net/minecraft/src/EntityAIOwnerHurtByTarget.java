package net.minecraft.src;

public class EntityAIOwnerHurtByTarget extends EntityAITarget
{
    EntityTameable theDefendingTameable;
    EntityLiving theOwnerAttacker;
    
    public EntityAIOwnerHurtByTarget(final EntityTameable par1EntityTameable) {
        super(par1EntityTameable, 32.0f, false);
        this.theDefendingTameable = par1EntityTameable;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theDefendingTameable.isTamed()) {
            return false;
        }
        final EntityLiving var1 = this.theDefendingTameable.getOwner();
        if (var1 == null) {
            return false;
        }
        this.theOwnerAttacker = var1.getAITarget();
        return this.isSuitableTarget(this.theOwnerAttacker, false);
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theOwnerAttacker);
        super.startExecuting();
    }
}
