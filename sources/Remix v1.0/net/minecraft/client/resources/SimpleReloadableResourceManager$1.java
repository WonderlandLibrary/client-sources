package net.minecraft.client.resources;

import com.google.common.base.*;

class SimpleReloadableResourceManager$1 implements Function {
    public String apply(final IResourcePack p_apply_1_) {
        return p_apply_1_.getPackName();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((IResourcePack)p_apply_1_);
    }
}