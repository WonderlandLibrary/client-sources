package com.google.gson.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface JsonAdapter {
    Class<?> value();
    
    boolean nullSafe() default true;
}
