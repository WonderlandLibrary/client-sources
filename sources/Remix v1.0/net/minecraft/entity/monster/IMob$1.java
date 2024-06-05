package net.minecraft.entity.monster;

import com.google.common.base.*;
import net.minecraft.entity.*;

static final class IMob$1 implements Predicate {
    public boolean func_179983_a(final Entity p_179983_1_) {
        return p_179983_1_ instanceof IMob;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179983_a((Entity)p_apply_1_);
    }
}