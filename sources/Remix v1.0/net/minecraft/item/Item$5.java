package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;

static final class Item$5 implements Function {
    public String apply(final ItemStack stack) {
        return BlockSand.EnumType.func_176686_a(stack.getMetadata()).func_176685_d();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}