package net.minecraft.block.state;

import com.google.common.base.*;
import net.minecraft.block.properties.*;

static final class BlockState$1 implements Function {
    public String apply(final IProperty property) {
        return (property == null) ? "<NULL>" : property.getName();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((IProperty)p_apply_1_);
    }
}