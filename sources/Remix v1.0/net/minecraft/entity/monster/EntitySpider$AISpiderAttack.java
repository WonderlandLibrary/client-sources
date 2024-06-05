package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

class AISpiderAttack extends EntityAIAttackOnCollide
{
    public AISpiderAttack(final Class p_i45819_2_) {
        super(EntitySpider.this, p_i45819_2_, 1.0, true);
    }
    
    @Override
    public boolean continueExecuting() {
        final float var1 = this.attacker.getBrightness(1.0f);
        if (var1 >= 0.5f && this.attacker.getRNG().nextInt(100) == 0) {
            this.attacker.setAttackTarget(null);
            return false;
        }
        return super.continueExecuting();
    }
    
    @Override
    protected double func_179512_a(final EntityLivingBase p_179512_1_) {
        return 4.0f + p_179512_1_.width;
    }
}
