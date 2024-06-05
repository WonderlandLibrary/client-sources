package net.minecraft.src;

import java.util.concurrent.*;

class CallableEffectID implements Callable
{
    final PotionEffect field_102034_a;
    final EntityLiving field_102033_b;
    
    CallableEffectID(final EntityLiving par1EntityLiving, final PotionEffect par2PotionEffect) {
        this.field_102033_b = par1EntityLiving;
        this.field_102034_a = par2PotionEffect;
    }
    
    public String func_102032_a() {
        return new StringBuilder(String.valueOf(this.field_102034_a.getPotionID())).toString();
    }
    
    @Override
    public Object call() {
        return this.func_102032_a();
    }
}
