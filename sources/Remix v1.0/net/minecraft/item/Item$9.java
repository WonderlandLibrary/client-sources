package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;

static final class Item$9 implements Function {
    public String apply(final ItemStack stack) {
        return BlockSandStone.EnumType.func_176673_a(stack.getMetadata()).func_176676_c();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}