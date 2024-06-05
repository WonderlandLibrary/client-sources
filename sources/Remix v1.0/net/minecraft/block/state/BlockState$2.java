package net.minecraft.block.state;

import java.util.*;
import net.minecraft.block.properties.*;

class BlockState$2 implements Comparator {
    public int compare(final IProperty left, final IProperty right) {
        return left.getName().compareTo(right.getName());
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.compare((IProperty)p_compare_1_, (IProperty)p_compare_2_);
    }
}