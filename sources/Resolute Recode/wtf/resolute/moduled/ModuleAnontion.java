package wtf.resolute.moduled;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface ModuleAnontion {
    String name();
    String server();
    int key() default 0;
    Categories type();
}
