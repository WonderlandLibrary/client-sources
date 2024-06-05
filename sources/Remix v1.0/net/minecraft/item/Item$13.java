package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;

static final class Item$13 implements Function {
    public String apply(final ItemStack stack) {
        return BlockStoneBrick.EnumType.getStateFromMeta(stack.getMetadata()).getVariantName();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}