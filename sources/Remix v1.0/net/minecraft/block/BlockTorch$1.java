package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.util.*;

static final class BlockTorch$1 implements Predicate {
    public boolean func_176601_a(final EnumFacing p_176601_1_) {
        return p_176601_1_ != EnumFacing.DOWN;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_176601_a((EnumFacing)p_apply_1_);
    }
}