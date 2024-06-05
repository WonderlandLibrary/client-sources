package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest
{
    private final EntityVillager theMerchant;
    
    public EntityAILookAtTradePlayer(final EntityVillager p_i1633_1_) {
        super(p_i1633_1_, EntityPlayer.class, 8.0f);
        this.theMerchant = p_i1633_1_;
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
