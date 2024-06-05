package net.minecraft.world;

import com.google.common.base.*;
import net.minecraft.entity.*;

class WorldServer$1 implements Predicate {
    public boolean func_180242_a(final EntityLivingBase p_180242_1_) {
        return p_180242_1_ != null && p_180242_1_.isEntityAlive() && WorldServer.this.isAgainstSky(p_180242_1_.getPosition());
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180242_a((EntityLivingBase)p_apply_1_);
    }
}