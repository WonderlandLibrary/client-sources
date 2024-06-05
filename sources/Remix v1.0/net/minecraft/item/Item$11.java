package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;

static final class Item$11 implements Function {
    public String apply(final ItemStack stack) {
        return BlockFlower.EnumFlowerType.func_176967_a(BlockFlower.EnumFlowerColor.RED, stack.getMetadata()).func_176963_d();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}