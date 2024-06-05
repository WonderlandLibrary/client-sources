package net.minecraft.item;

import com.google.common.base.*;

static final class Item$8 implements Function {
    public String apply(final ItemStack stack) {
        return ((stack.getMetadata() & 0x1) == 0x1) ? "wet" : "dry";
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}