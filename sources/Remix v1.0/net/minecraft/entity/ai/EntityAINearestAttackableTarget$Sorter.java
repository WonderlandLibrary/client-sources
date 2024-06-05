package net.minecraft.entity.ai;

import java.util.*;
import net.minecraft.entity.*;

public static class Sorter implements Comparator
{
    private final Entity theEntity;
    
    public Sorter(final Entity p_i1662_1_) {
        this.theEntity = p_i1662_1_;
    }
    
    public int compare(final Entity p_compare_1_, final Entity p_compare_2_) {
        final double var3 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
        final double var4 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
        return (var3 < var4) ? -1 : ((var3 > var4) ? 1 : 0);
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.compare((Entity)p_compare_1_, (Entity)p_compare_2_);
    }
}
