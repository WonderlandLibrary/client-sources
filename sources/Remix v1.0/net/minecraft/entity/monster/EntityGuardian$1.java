package net.minecraft.entity.monster;

import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

class EntityGuardian$1 implements Predicate {
    public boolean func_179913_a(final EntityPlayerMP p_179913_1_) {
        return EntityGuardian.this.getDistanceSqToEntity(p_179913_1_) < 2500.0 && p_179913_1_.theItemInWorldManager.func_180239_c();
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179913_a((EntityPlayerMP)p_apply_1_);
    }
}