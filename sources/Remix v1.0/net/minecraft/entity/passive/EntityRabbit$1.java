package net.minecraft.entity.passive;

import com.google.common.base.*;
import net.minecraft.entity.*;

class EntityRabbit$1 implements Predicate {
    public boolean func_180086_a(final Entity p_180086_1_) {
        return p_180086_1_ instanceof EntityWolf;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180086_a((Entity)p_apply_1_);
    }
}