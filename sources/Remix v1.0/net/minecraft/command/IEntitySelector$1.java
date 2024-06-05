package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;

static final class IEntitySelector$1 implements Predicate {
    public boolean func_180131_a(final Entity p_180131_1_) {
        return p_180131_1_.isEntityAlive();
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180131_a((Entity)p_apply_1_);
    }
}