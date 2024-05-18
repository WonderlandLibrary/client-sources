package net.minecraft.src;

public class EntityAITradePlayer extends EntityAIBase
{
    private EntityVillager villager;
    
    public EntityAITradePlayer(final EntityVillager par1EntityVillager) {
        this.villager = par1EntityVillager;
        this.setMutexBits(5);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.villager.isEntityAlive()) {
            return false;
        }
        if (this.villager.isInWater()) {
            return false;
        }
        if (!this.villager.onGround) {
            return false;
        }
        if (this.villager.velocityChanged) {
            return false;
        }
        final EntityPlayer var1 = this.villager.getCustomer();
        return var1 != null && this.villager.getDistanceSqToEntity(var1) <= 16.0 && var1.openContainer instanceof Container;
    }
    
    @Override
    public void startExecuting() {
        this.villager.getNavigator().clearPathEntity();
    }
    
    @Override
    public void resetTask() {
        this.villager.setCustomer(null);
    }
}
