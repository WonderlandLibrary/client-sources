package net.minecraft.src;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest
{
    private final EntityVillager theMerchant;
    
    public EntityAILookAtTradePlayer(final EntityVillager par1EntityVillager) {
        super(par1EntityVillager, EntityPlayer.class, 8.0f);
        this.theMerchant = par1EntityVillager;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theMerchant.isTrading()) {
            this.closestEntity = this.theMerchant.getCustomer();
            return true;
        }
        return false;
    }
}
