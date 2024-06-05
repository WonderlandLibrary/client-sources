package net.minecraft.entity.monster;

import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;

class EntityCreeper$1 implements Predicate {
    public boolean func_179958_a(final Entity p_179958_1_) {
        return p_179958_1_ instanceof EntityOcelot;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179958_a((Entity)p_apply_1_);
    }
}