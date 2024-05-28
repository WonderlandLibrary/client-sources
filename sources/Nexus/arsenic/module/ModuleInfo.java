package arsenic.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

    String name();

    String description() default "placeholder lol";

    ModuleCategory category();

    int keybind() default 0;

    boolean enabled() default false;

    boolean hidden() default false;

}
