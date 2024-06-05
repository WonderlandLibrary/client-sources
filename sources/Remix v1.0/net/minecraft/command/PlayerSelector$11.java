package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

static final class PlayerSelector$11 implements Predicate {
    final /* synthetic */ AxisAlignedBB val$var19;
    
    public boolean func_179609_a(final Entity p_179609_1_) {
        return p_179609_1_.posX >= this.val$var19.minX && p_179609_1_.posY >= this.val$var19.minY && p_179609_1_.posZ >= this.val$var19.minZ && (p_179609_1_.posX < this.val$var19.maxX && p_179609_1_.posY < this.val$var19.maxY && p_179609_1_.posZ < this.val$var19.maxZ);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179609_a((Entity)p_apply_1_);
    }
}