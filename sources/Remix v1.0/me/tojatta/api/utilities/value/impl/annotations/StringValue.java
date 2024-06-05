package me.tojatta.api.utilities.value.impl.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface StringValue {
    String label();
}
