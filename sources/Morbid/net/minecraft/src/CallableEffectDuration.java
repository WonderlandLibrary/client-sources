package net.minecraft.src;

import java.util.concurrent.*;

class CallableEffectDuration implements Callable
{
    final PotionEffect field_102037_a;
    final EntityLiving field_102036_b;
    
    CallableEffectDuration(final EntityLiving par1EntityLiving, final PotionEffect par2PotionEffect) {
        this.field_102036_b = par1EntityLiving;
        this.field_102037_a = par2PotionEffect;
    }
    
    public String func_102035_a() {
        return new StringBuilder(String.valueOf(this.field_102037_a.getDuration())).toString();
    }
    
    @Override
    public Object call() {
        return this.func_102035_a();
    }
}
