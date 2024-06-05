package net.minecraft.client.resources.model;

import com.google.common.collect.*;
import net.minecraft.util.*;

static class MyWeighedRandomItem extends WeightedRandom.Item implements Comparable
{
    protected final IBakedModel model;
    
    public MyWeighedRandomItem(final IBakedModel p_i46072_1_, final int p_i46072_2_) {
        super(p_i46072_2_);
        this.model = p_i46072_1_;
    }
    
    public int func_177634_a(final MyWeighedRandomItem p_177634_1_) {
        return ComparisonChain.start().compare(p_177634_1_.itemWeight, this.itemWeight).compare(this.func_177635_a(), p_177634_1_.func_177635_a()).result();
    }
    
    protected int func_177635_a() {
        int var1 = this.model.func_177550_a().size();
        for (final EnumFacing var5 : EnumFacing.values()) {
            var1 += this.model.func_177551_a(var5).size();
        }
        return var1;
    }
    
    @Override
    public String toString() {
        return "MyWeighedRandomItem{weight=" + this.itemWeight + ", model=" + this.model + '}';
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.func_177634_a((MyWeighedRandomItem)p_compareTo_1_);
    }
}
