package net.minecraft.entity.monster;

import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

class GuardianTargetSelector implements Predicate
{
    private EntityGuardian field_179916_a;
    
    GuardianTargetSelector() {
        this.field_179916_a = EntityGuardian.this;
    }
    
    public boolean func_179915_a(final EntityLivingBase p_179915_1_) {
        return (p_179915_1_ instanceof EntityPlayer || p_179915_1_ instanceof EntitySquid) && p_179915_1_.getDistanceSqToEntity(this.field_179916_a) > 9.0;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179915_a((EntityLivingBase)p_apply_1_);
    }
}
