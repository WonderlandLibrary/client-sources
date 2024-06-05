package net.minecraft.entity.ai;

import com.google.common.base.*;
import net.minecraft.entity.*;

class EntityAIFindEntityNearest$1 implements Predicate {
    public boolean func_179876_a(final EntityLivingBase p_179876_1_) {
        double var2 = EntityAIFindEntityNearest.this.func_179438_f();
        if (p_179876_1_.isSneaking()) {
            var2 *= 0.800000011920929;
        }
        return !p_179876_1_.isInvisible() && p_179876_1_.getDistanceToEntity(EntityAIFindEntityNearest.access$000(EntityAIFindEntityNearest.this)) <= var2 && EntityAITarget.func_179445_a(EntityAIFindEntityNearest.access$000(EntityAIFindEntityNearest.this), p_179876_1_, false, true);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179876_a((EntityLivingBase)p_apply_1_);
    }
}