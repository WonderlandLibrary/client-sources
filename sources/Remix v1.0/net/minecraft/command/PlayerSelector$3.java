package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

static final class PlayerSelector$3 implements Predicate {
    final /* synthetic */ int val$var2;
    final /* synthetic */ int val$var3;
    
    public boolean func_179625_a(final Entity p_179625_1_) {
        if (!(p_179625_1_ instanceof EntityPlayerMP)) {
            return false;
        }
        final EntityPlayerMP var2x = (EntityPlayerMP)p_179625_1_;
        return (this.val$var2 <= -1 || var2x.experienceLevel >= this.val$var2) && (this.val$var3 <= -1 || var2x.experienceLevel <= this.val$var3);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179625_a((Entity)p_apply_1_);
    }
}