package net.minecraft.entity.ai;

import com.google.common.base.*;
import net.minecraft.entity.*;

class EntityAIAvoidEntity$1 implements Predicate {
    public boolean func_180419_a(final Entity p_180419_1_) {
        return p_180419_1_.isEntityAlive() && EntityAIAvoidEntity.this.theEntity.getEntitySenses().canSee(p_180419_1_);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180419_a((Entity)p_apply_1_);
    }
}