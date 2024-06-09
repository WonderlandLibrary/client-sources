package org.spongepowered.asm.mixin;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Debug {
    boolean export() default false;
    
    boolean print() default false;
}
