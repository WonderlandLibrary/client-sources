package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

class BlockRedstoneComparator$1 implements Predicate {
    final /* synthetic */ EnumFacing val$p_176461_2_;
    
    public boolean func_180416_a(final Entity p_180416_1_) {
        return p_180416_1_ != null && p_180416_1_.func_174811_aO() == this.val$p_176461_2_;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180416_a((Entity)p_apply_1_);
    }
}