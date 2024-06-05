package me.tojatta.api.utilities.value.impl.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface NumberValue {
    String label();
    
    String minimum() default "0";
    
    String maximum() default "20";
}
