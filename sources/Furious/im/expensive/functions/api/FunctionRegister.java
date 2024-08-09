package im.expensive.functions.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface FunctionRegister {
    String name();
    int key() default 0;
    Category type();
}
