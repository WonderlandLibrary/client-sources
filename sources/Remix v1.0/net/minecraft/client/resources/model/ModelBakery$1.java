package net.minecraft.client.resources.model;

import java.util.*;

class ModelBakery$1 implements Comparator {
    public int func_177505_a(final ModelResourceLocation p_177505_1_, final ModelResourceLocation p_177505_2_) {
        return p_177505_1_.toString().compareTo(p_177505_2_.toString());
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.func_177505_a((ModelResourceLocation)p_compare_1_, (ModelResourceLocation)p_compare_2_);
    }
}