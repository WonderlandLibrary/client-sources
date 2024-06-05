package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

static class AINearestAttackableTargetNonCreeper extends EntityAINearestAttackableTarget
{
    public AINearestAttackableTargetNonCreeper(final EntityCreature p_i45858_1_, final Class p_i45858_2_, final int p_i45858_3_, final boolean p_i45858_4_, final boolean p_i45858_5_, final Predicate p_i45858_6_) {
        super(p_i45858_1_, p_i45858_2_, p_i45858_3_, p_i45858_4_, p_i45858_5_, p_i45858_6_);
        this.targetEntitySelector = (Predicate)new Predicate() {
            public boolean func_180096_a(final EntityLivingBase p_180096_1_) {
                if (p_i45858_6_ != null && !p_i45858_6_.apply((Object)p_180096_1_)) {
                    return false;
                }
                if (p_180096_1_ instanceof EntityCreeper) {
                    return false;
                }
                if (p_180096_1_ instanceof EntityPlayer) {
                    double var2 = EntityAITarget.this.getTargetDistance();
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
                    if (p_180096_1_.getDistanceToEntity(p_i45858_1_) > var2) {
                        return false;
                    }
                }
                return EntityAITarget.this.isSuitableTarget(p_180096_1_, false);
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.func_180096_a((EntityLivingBase)p_apply_1_);
            }
        };
    }
}
