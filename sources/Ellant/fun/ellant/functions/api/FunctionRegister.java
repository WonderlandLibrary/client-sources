package fun.ellant.functions.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import fun.ellant.functions.api.Category;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface FunctionRegister {
    public String name();
    public int key() default 0;
    public Category type();
    public String desc();

}
