package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;

static final class Item$17 implements Function {
    public String apply(final ItemStack stack) {
        return BlockRedSandstone.EnumType.func_176825_a(stack.getMetadata()).func_176828_c();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}