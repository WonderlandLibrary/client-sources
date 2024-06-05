package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

static final class PlayerSelector$8 implements Predicate {
    final /* synthetic */ BlockPos val$p_180698_1_;
    final /* synthetic */ int val$var3;
    final /* synthetic */ int val$var5;
    final /* synthetic */ int val$var4;
    final /* synthetic */ int val$var6;
    
    public boolean func_179594_a(final Entity p_179594_1_) {
        final int var2 = (int)p_179594_1_.func_174831_c(this.val$p_180698_1_);
        return (this.val$var3 < 0 || var2 >= this.val$var5) && (this.val$var4 < 0 || var2 <= this.val$var6);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179594_a((Entity)p_apply_1_);
    }
}