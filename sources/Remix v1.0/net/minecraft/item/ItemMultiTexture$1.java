package net.minecraft.item;

import com.google.common.base.*;

class ItemMultiTexture$1 implements Function {
    final /* synthetic */ String[] val$p_i45346_3_;
    
    public String apply(final ItemStack p_179541_1_) {
        int var2 = p_179541_1_.getMetadata();
        if (var2 < 0 || var2 >= this.val$p_i45346_3_.length) {
            var2 = 0;
        }
        return this.val$p_i45346_3_[var2];
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((ItemStack)p_apply_1_);
    }
}