package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.*;
import org.spongepowered.asm.lib.*;

class MixinInfo$1 implements Function<Type, String> {
    final MixinInfo this$0;
    
    MixinInfo$1(final MixinInfo this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public String apply(final Type type) {
        return type.getClassName();
    }
    
    @Override
    public Object apply(final Object o) {
        return this.apply((Type)o);
    }
}