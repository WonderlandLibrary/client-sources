package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.*;

class MixinInfo$2 implements Function<String, String> {
    final MixinInfo this$0;
    
    MixinInfo$2(final MixinInfo this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public String apply(final String s) {
        return this.this$0.getParent().remapClassName(this.this$0.getClassRef(), s);
    }
    
    @Override
    public Object apply(final Object o) {
        return this.apply((String)o);
    }
}