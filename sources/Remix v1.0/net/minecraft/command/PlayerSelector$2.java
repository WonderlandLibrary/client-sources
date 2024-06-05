package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;

static final class PlayerSelector$2 implements Predicate {
    final /* synthetic */ String val$var3_f;
    final /* synthetic */ boolean val$var4;
    
    public boolean func_179613_a(final Entity p_179613_1_) {
        return EntityList.func_180123_a(p_179613_1_, this.val$var3_f) != this.val$var4;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179613_a((Entity)p_apply_1_);
    }
}