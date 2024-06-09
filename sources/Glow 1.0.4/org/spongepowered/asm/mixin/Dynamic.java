package org.spongepowered.asm.mixin;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.CLASS)
public @interface Dynamic {
    String value() default "";
    
    Class<?> mixin() default void.class;
}
