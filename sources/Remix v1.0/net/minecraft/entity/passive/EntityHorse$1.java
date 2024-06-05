package net.minecraft.entity.passive;

import com.google.common.base.*;
import net.minecraft.entity.*;

static final class EntityHorse$1 implements Predicate {
    public boolean func_179873_a(final Entity p_179873_1_) {
        return p_179873_1_ instanceof EntityHorse && ((EntityHorse)p_179873_1_).func_110205_ce();
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179873_a((Entity)p_apply_1_);
    }
}