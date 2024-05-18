package net.minecraft.src;

import java.util.concurrent.*;

class CallableEffectIsSplash implements Callable
{
    final PotionEffect field_102043_a;
    final EntityLiving field_102042_b;
    
    CallableEffectIsSplash(final EntityLiving par1EntityLiving, final PotionEffect par2PotionEffect) {
        this.field_102042_b = par1EntityLiving;
        this.field_102043_a = par2PotionEffect;
    }
    
    public String func_102041_a() {
        return new StringBuilder(String.valueOf(this.field_102043_a.isSplashPotionEffect())).toString();
    }
    
    @Override
    public Object call() {
        return this.func_102041_a();
    }
}
