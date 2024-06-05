package net.minecraft.entity.passive;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

class AIEvilAttack extends EntityAIAttackOnCollide
{
    public AIEvilAttack() {
        super(EntityRabbit.this, EntityLivingBase.class, 1.4, true);
    }
    
    @Override
    protected double func_179512_a(final EntityLivingBase p_179512_1_) {
        return 4.0f + p_179512_1_.width;
    }
}
