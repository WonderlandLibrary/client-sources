package net.minecraft.block;

import com.google.common.base.*;

static final class BlockFlower$EnumFlowerType$1 implements Predicate {
    final /* synthetic */ EnumFlowerColor val$var3;
    
    public boolean func_180350_a(final EnumFlowerType p_180350_1_) {
        return p_180350_1_.func_176964_a() == this.val$var3;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180350_a((EnumFlowerType)p_apply_1_);
    }
}