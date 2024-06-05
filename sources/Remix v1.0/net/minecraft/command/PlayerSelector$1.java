package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

static final class PlayerSelector$1 implements Predicate {
    public boolean func_179624_a(final Entity p_179624_1_) {
        return p_179624_1_ instanceof EntityPlayer;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179624_a((Entity)p_apply_1_);
    }
}