package net.minecraft.entity.ai;

import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

class EntityAINearestAttackableTarget$1 implements Predicate {
    final /* synthetic */ Predicate val$p_i45880_6_;
    
    public boolean func_179878_a(final EntityLivingBase p_179878_1_) {
        if (this.val$p_i45880_6_ != null && !this.val$p_i45880_6_.apply((Object)p_179878_1_)) {
            return false;
        }
        if (p_179878_1_ instanceof EntityPlayer) {
            double var2 = EntityAINearestAttackableTarget.this.getTargetDistance();
            if (p_179878_1_.isSneaking()) {
                var2 *= 0.800000011920929;
            }
            if (p_179878_1_.isInvisible()) {
                float var3 = ((EntityPlayer)p_179878_1_).getArmorVisibility();
                if (var3 < 0.1f) {
                    var3 = 0.1f;
                }
                var2 *= 0.7f * var3;
            }
            if (p_179878_1_.getDistanceToEntity(EntityAINearestAttackableTarget.this.taskOwner) > var2) {
                return false;
            }
        }
        return EntityAINearestAttackableTarget.this.isSuitableTarget(p_179878_1_, false);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179878_a((EntityLivingBase)p_apply_1_);
    }
}