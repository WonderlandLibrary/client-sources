package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

static final class IEntitySelector$4 implements Predicate {
    public boolean func_180103_a(final Entity p_180103_1_) {
        return !(p_180103_1_ instanceof EntityPlayer) || !((EntityPlayer)p_180103_1_).func_175149_v();
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180103_a((Entity)p_apply_1_);
    }
}