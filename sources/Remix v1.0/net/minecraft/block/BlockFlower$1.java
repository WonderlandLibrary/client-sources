package net.minecraft.block;

import com.google.common.base.*;

class BlockFlower$1 implements Predicate {
    public boolean func_180354_a(final EnumFlowerType p_180354_1_) {
        return p_180354_1_.func_176964_a() == BlockFlower.this.func_176495_j();
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180354_a((EnumFlowerType)p_apply_1_);
    }
}