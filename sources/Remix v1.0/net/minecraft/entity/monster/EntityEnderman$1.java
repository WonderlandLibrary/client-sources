package net.minecraft.entity.monster;

import com.google.common.base.*;

class EntityEnderman$1 implements Predicate {
    public boolean func_179948_a(final EntityEndermite p_179948_1_) {
        return p_179948_1_.isSpawnedByPlayer();
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179948_a((EntityEndermite)p_apply_1_);
    }
}