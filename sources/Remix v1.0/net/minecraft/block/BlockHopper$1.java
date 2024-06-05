package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.util.*;

static final class BlockHopper$1 implements Predicate {
    public boolean func_180180_a(final EnumFacing p_180180_1_) {
        return p_180180_1_ != EnumFacing.UP;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180180_a((EnumFacing)p_apply_1_);
    }
}