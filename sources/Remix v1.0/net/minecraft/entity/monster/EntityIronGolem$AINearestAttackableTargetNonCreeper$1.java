package net.minecraft.entity.monster;

import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

class EntityIronGolem$AINearestAttackableTargetNonCreeper$1 implements Predicate {
    final /* synthetic */ Predicate val$p_i45858_6_;
    final /* synthetic */ EntityCreature val$p_i45858_1_;
    
    public boolean func_180096_a(final EntityLivingBase p_180096_1_) {
        if (this.val$p_i45858_6_ != null && !this.val$p_i45858_6_.apply((Object)p_180096_1_)) {
            return false;
        }
        if (p_180096_1_ instanceof EntityCreeper) {
            return false;
        }
        if (p_180096_1_ instanceof EntityPlayer) {
            double var2 = AINearestAttackableTargetNonCreeper.access$000(AINearestAttackableTargetNonCreeper.this);
            if (p_180096_1_.isSneaking()) {
                var2 *= 0.800000011920929;
            }
            if (p_180096_1_.isInvisible()) {
                float var3 = ((EntityPlayer)p_180096_1_).getArmorVisibility();
                if (var3 < 0.1f) {
                    var3 = 0.1f;
                }
                var2 *= 0.7f * var3;
            }
            if (p_180096_1_.getDistanceToEntity(this.val$p_i45858_1_) > var2) {
                return false;
            }
        }
        return AINearestAttackableTargetNonCreeper.access$100(AINearestAttackableTargetNonCreeper.this, p_180096_1_, false);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180096_a((EntityLivingBase)p_apply_1_);
    }
}