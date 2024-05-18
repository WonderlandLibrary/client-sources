package net.minecraft.src;

import java.util.concurrent.*;

class CallableEffectIsAmbient implements Callable
{
    final PotionEffect field_102046_a;
    final EntityLiving field_102045_b;
    
    CallableEffectIsAmbient(final EntityLiving par1EntityLiving, final PotionEffect par2PotionEffect) {
        this.field_102045_b = par1EntityLiving;
        this.field_102046_a = par2PotionEffect;
    }
    
    public String func_102044_a() {
        return new StringBuilder(String.valueOf(this.field_102046_a.getIsAmbient())).toString();
    }
    
    @Override
    public Object call() {
        return this.func_102044_a();
    }
}
