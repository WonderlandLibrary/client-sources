package net.minecraft.client.renderer.block.model;

import java.util.*;

public static class Variants
{
    private final String field_178423_a;
    private final List field_178422_b;
    
    public Variants(final String p_i46218_1_, final List p_i46218_2_) {
        this.field_178423_a = p_i46218_1_;
        this.field_178422_b = p_i46218_2_;
    }
    
    public List getVariants() {
        return this.field_178422_b;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof Variants)) {
            return false;
        }
        final Variants var2 = (Variants)p_equals_1_;
        return this.field_178423_a.equals(var2.field_178423_a) && this.field_178422_b.equals(var2.field_178422_b);
    }
    
    @Override
    public int hashCode() {
        int var1 = this.field_178423_a.hashCode();
        var1 = 31 * var1 + this.field_178422_b.hashCode();
        return var1;
    }
}
