package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;

static final class Item$1 implements Function {
    public String apply(final ItemStack stack) {
        return BlockStone.EnumType.getStateFromMeta(stack.getMetadata()).func_176644_c();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}