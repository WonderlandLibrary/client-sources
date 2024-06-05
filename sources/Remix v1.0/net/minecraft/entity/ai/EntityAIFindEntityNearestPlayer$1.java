package net.minecraft.entity.ai;

import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

class EntityAIFindEntityNearestPlayer$1 implements Predicate {
    public boolean func_179880_a(final Entity p_179880_1_) {
        if (!(p_179880_1_ instanceof EntityPlayer)) {
            return false;
        }
        double var2 = EntityAIFindEntityNearestPlayer.this.func_179431_f();
        if (p_179880_1_.isSneaking()) {
            var2 *= 0.800000011920929;
        }
        if (p_179880_1_.isInvisible()) {
            float var3 = ((EntityPlayer)p_179880_1_).getArmorVisibility();
            if (var3 < 0.1f) {
                var3 = 0.1f;
            }
            var2 *= 0.7f * var3;
        }
        return p_179880_1_.getDistanceToEntity(EntityAIFindEntityNearestPlayer.access$000(EntityAIFindEntityNearestPlayer.this)) <= var2 && EntityAITarget.func_179445_a(EntityAIFindEntityNearestPlayer.access$000(EntityAIFindEntityNearestPlayer.this), (EntityLivingBase)p_179880_1_, false, true);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179880_a((Entity)p_apply_1_);
    }
}