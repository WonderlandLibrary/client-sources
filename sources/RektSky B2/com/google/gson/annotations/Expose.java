package com.google.gson.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Expose {
    boolean serialize() default true;
    
    boolean deserialize() default true;
}
