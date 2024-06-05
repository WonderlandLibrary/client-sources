package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;

static final class PlayerSelector$9 implements Predicate {
    final /* synthetic */ int val$var2;
    final /* synthetic */ int val$var3;
    
    public boolean func_179591_a(final Entity p_179591_1_) {
        final int var2x = PlayerSelector.func_179650_a((int)Math.floor(p_179591_1_.rotationYaw));
        return (this.val$var2 > this.val$var3) ? (var2x >= this.val$var2 || var2x <= this.val$var3) : (var2x >= this.val$var2 && var2x <= this.val$var3);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179591_a((Entity)p_apply_1_);
    }
}