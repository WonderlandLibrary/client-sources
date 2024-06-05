package net.minecraft.src;

import java.util.concurrent.*;

class CallableEffectAmplifier implements Callable
{
    final PotionEffect field_102040_a;
    final EntityLiving field_102039_b;
    
    CallableEffectAmplifier(final EntityLiving par1EntityLiving, final PotionEffect par2PotionEffect) {
        this.field_102039_b = par1EntityLiving;
        this.field_102040_a = par2PotionEffect;
    }
    
    public String func_102038_a() {
        return new StringBuilder(String.valueOf(this.field_102040_a.getAmplifier())).toString();
    }
    
    @Override
    public Object call() {
        return this.func_102038_a();
    }
}
