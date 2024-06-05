package net.minecraft.entity.monster;

import com.google.common.base.*;
import net.minecraft.entity.*;

static final class IMob$2 implements Predicate {
    public boolean func_179982_a(final Entity p_179982_1_) {
        return p_179982_1_ instanceof IMob && !p_179982_1_.isInvisible();
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179982_a((Entity)p_apply_1_);
    }
}