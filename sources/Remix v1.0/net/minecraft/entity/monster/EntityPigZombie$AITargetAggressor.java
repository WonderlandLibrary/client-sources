package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

class AITargetAggressor extends EntityAINearestAttackableTarget
{
    public AITargetAggressor() {
        super(EntityPigZombie.this, EntityPlayer.class, true);
    }
    
    @Override
    public boolean shouldExecute() {
        return ((EntityPigZombie)this.taskOwner).func_175457_ck() && super.shouldExecute();
    }
}
