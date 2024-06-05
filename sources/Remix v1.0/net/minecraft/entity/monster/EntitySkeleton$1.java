package net.minecraft.entity.monster;

import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;

class EntitySkeleton$1 implements Predicate {
    public boolean func_179945_a(final Entity p_179945_1_) {
        return p_179945_1_ instanceof EntityWolf;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179945_a((Entity)p_apply_1_);
    }
}