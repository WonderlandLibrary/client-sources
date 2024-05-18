package org.spongepowered.asm.mixin.transformer.meta;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MixinMerged {
    String mixin();
    
    int priority();
}
