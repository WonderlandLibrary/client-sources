package net.minecraft.block.state;

import com.google.common.base.*;

static final class BlockWorldState$1 implements Predicate {
    final /* synthetic */ Predicate val$p_177510_0_;
    
    public boolean func_177503_a(final BlockWorldState p_177503_1_) {
        return p_177503_1_ != null && this.val$p_177510_0_.apply((Object)p_177503_1_.func_177509_a());
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_177503_a((BlockWorldState)p_apply_1_);
    }
}