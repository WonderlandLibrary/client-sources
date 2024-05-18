package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface Slice {
    String id() default "";
    
    At from() default @At("HEAD");
    
    At to() default @At("TAIL");
}
