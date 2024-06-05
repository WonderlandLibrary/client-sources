package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;

static final class Item$3 implements Function {
    public String apply(final ItemStack stack) {
        return BlockPlanks.EnumType.func_176837_a(stack.getMetadata()).func_176840_c();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}