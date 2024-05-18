package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifyVariable {
    String[] method();
    
    Slice slice() default @Slice;
    
    At at();
    
    boolean print() default false;
    
    int ordinal() default -1;
    
    int index() default -1;
    
    String[] name() default {};
    
    boolean argsOnly() default false;
    
    boolean remap() default true;
    
    int require() default -1;
    
    int expect() default 1;
    
    int allow() default -1;
    
    String constraints() default "";
}
