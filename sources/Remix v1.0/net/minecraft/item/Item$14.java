package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;

static final class Item$14 implements Function {
    public String apply(final ItemStack stack) {
        return BlockWall.EnumType.func_176660_a(stack.getMetadata()).func_176659_c();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}