package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;

static final class PlayerSelector$10 implements Predicate {
    final /* synthetic */ int val$var2;
    final /* synthetic */ int val$var3;
    
    public boolean func_179616_a(final Entity p_179616_1_) {
        final int var2x = PlayerSelector.func_179650_a((int)Math.floor(p_179616_1_.rotationPitch));
        return (this.val$var2 > this.val$var3) ? (var2x >= this.val$var2 || var2x <= this.val$var3) : (var2x >= this.val$var2 && var2x <= this.val$var3);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179616_a((Entity)p_apply_1_);
    }
}