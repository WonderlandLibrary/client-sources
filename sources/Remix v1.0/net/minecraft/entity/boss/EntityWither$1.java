package net.minecraft.entity.boss;

import com.google.common.base.*;
import net.minecraft.entity.*;

static final class EntityWither$1 implements Predicate {
    public boolean func_180027_a(final Entity p_180027_1_) {
        return p_180027_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_180027_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180027_a((Entity)p_apply_1_);
    }
}