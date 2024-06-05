package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;

static final class IEntitySelector$3 implements Predicate {
    public boolean func_180102_a(final Entity p_180102_1_) {
        return p_180102_1_ instanceof IInventory && p_180102_1_.isEntityAlive();
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180102_a((Entity)p_apply_1_);
    }
}