package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.util.*;

static final class BlockStem$1 implements Predicate {
    public boolean apply(final EnumFacing p_177218_1_) {
        return p_177218_1_ != EnumFacing.DOWN;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.apply((EnumFacing)p_apply_1_);
    }
}