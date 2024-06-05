package net.minecraft.entity.passive;

import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

class EntityOcelot$1 implements Predicate {
    public boolean func_179874_a(final Entity p_179874_1_) {
        return p_179874_1_ instanceof EntityPlayer;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179874_a((Entity)p_apply_1_);
    }
}