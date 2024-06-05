package net.minecraft.entity.monster;

import com.google.common.base.*;
import net.minecraft.entity.*;

class EntityMob$1 implements Predicate {
    public boolean func_179911_a(final Entity p_179911_1_) {
        return p_179911_1_ instanceof EntityCreeper && ((EntityCreeper)p_179911_1_).getCreeperState() > 0;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179911_a((Entity)p_apply_1_);
    }
}