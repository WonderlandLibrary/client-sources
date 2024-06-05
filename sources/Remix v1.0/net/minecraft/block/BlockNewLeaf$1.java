package net.minecraft.block;

import com.google.common.base.*;

static final class BlockNewLeaf$1 implements Predicate {
    public boolean func_180195_a(final BlockPlanks.EnumType p_180195_1_) {
        return p_180195_1_.func_176839_a() >= 4;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180195_a((BlockPlanks.EnumType)p_apply_1_);
    }
}