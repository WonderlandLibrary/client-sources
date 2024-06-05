package net.minecraft.command;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;

static final class PlayerSelector$12 implements Comparator {
    final /* synthetic */ BlockPos val$p_179658_5_;
    
    public int func_179611_a(final Entity p_179611_1_, final Entity p_179611_2_) {
        return ComparisonChain.start().compare(p_179611_1_.getDistanceSq(this.val$p_179658_5_), p_179611_2_.getDistanceSq(this.val$p_179658_5_)).result();
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.func_179611_a((Entity)p_compare_1_, (Entity)p_compare_2_);
    }
}