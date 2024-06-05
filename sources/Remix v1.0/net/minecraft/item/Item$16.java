package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;

static final class Item$16 implements Function {
    public String apply(final ItemStack stack) {
        return BlockPrismarine.EnumType.func_176810_a(stack.getMetadata()).func_176809_c();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}