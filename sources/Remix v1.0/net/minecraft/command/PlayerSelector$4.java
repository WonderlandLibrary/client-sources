package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

static final class PlayerSelector$4 implements Predicate {
    final /* synthetic */ int val$var2;
    
    public boolean func_179619_a(final Entity p_179619_1_) {
        if (!(p_179619_1_ instanceof EntityPlayerMP)) {
            return false;
        }
        final EntityPlayerMP var2x = (EntityPlayerMP)p_179619_1_;
        return var2x.theItemInWorldManager.getGameType().getID() == this.val$var2;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179619_a((Entity)p_apply_1_);
    }
}