package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;

static final class PlayerSelector$7 implements Predicate {
    final /* synthetic */ String val$var2_f;
    final /* synthetic */ boolean val$var3;
    
    public boolean func_179600_a(final Entity p_179600_1_) {
        return p_179600_1_.getName().equals(this.val$var2_f) != this.val$var3;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179600_a((Entity)p_apply_1_);
    }
}