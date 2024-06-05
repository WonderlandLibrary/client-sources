package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

class AIHurtByAggressor extends EntityAIHurtByTarget
{
    public AIHurtByAggressor() {
        super(EntityPigZombie.this, true, new Class[0]);
    }
    
    @Override
    protected void func_179446_a(final EntityCreature p_179446_1_, final EntityLivingBase p_179446_2_) {
        super.func_179446_a(p_179446_1_, p_179446_2_);
        if (p_179446_1_ instanceof EntityPigZombie) {
            EntityPigZombie.access$000((EntityPigZombie)p_179446_1_, p_179446_2_);
        }
    }
}
