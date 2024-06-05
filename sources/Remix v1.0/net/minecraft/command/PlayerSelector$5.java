package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;

static final class PlayerSelector$5 implements Predicate {
    final /* synthetic */ String val$var2_f;
    final /* synthetic */ boolean val$var3;
    
    public boolean func_179621_a(final Entity p_179621_1_) {
        if (!(p_179621_1_ instanceof EntityLivingBase)) {
            return false;
        }
        final EntityLivingBase var2x = (EntityLivingBase)p_179621_1_;
        final Team var3x = var2x.getTeam();
        final String var4 = (var3x == null) ? "" : var3x.getRegisteredName();
        return var4.equals(this.val$var2_f) != this.val$var3;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179621_a((Entity)p_apply_1_);
    }
}