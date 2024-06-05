package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

class AISpiderTarget extends EntityAINearestAttackableTarget
{
    public AISpiderTarget(final Class p_i45818_2_) {
        super(EntitySpider.this, p_i45818_2_, true);
    }
    
    @Override
    public boolean shouldExecute() {
        final float var1 = this.taskOwner.getBrightness(1.0f);
        return var1 < 0.5f && super.shouldExecute();
    }
}
