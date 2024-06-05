package net.minecraft.src;

import java.util.concurrent.*;

class CallableEffectName implements Callable
{
    final PotionEffect field_102031_a;
    final EntityLiving field_102030_b;
    
    CallableEffectName(final EntityLiving par1EntityLiving, final PotionEffect par2PotionEffect) {
        this.field_102030_b = par1EntityLiving;
        this.field_102031_a = par2PotionEffect;
    }
    
    public String func_102029_a() {
        return Potion.potionTypes[this.field_102031_a.getPotionID()].getName();
    }
    
    @Override
    public Object call() {
        return this.func_102029_a();
    }
}
