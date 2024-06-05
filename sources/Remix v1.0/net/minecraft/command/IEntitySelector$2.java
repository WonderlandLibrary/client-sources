package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;

static final class IEntitySelector$2 implements Predicate {
    public boolean func_180130_a(final Entity p_180130_1_) {
        return p_180130_1_.isEntityAlive() && p_180130_1_.riddenByEntity == null && p_180130_1_.ridingEntity == null;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180130_a((Entity)p_apply_1_);
    }
}