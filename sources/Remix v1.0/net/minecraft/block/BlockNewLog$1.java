package net.minecraft.block;

import com.google.common.base.*;

static final class BlockNewLog$1 implements Predicate {
    public boolean func_180194_a(final BlockPlanks.EnumType p_180194_1_) {
        return p_180194_1_.func_176839_a() >= 4;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180194_a((BlockPlanks.EnumType)p_apply_1_);
    }
}