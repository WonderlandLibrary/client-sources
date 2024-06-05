package net.minecraft.entity.passive;

import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;

class EntityVillager$1 implements Predicate {
    public boolean func_179530_a(final Entity p_179530_1_) {
        return p_179530_1_ instanceof EntityZombie;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179530_a((Entity)p_apply_1_);
    }
}