package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;

static final class Item$12 implements Function {
    public String apply(final ItemStack stack) {
        return BlockSilverfish.EnumType.func_176879_a(stack.getMetadata()).func_176882_c();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}