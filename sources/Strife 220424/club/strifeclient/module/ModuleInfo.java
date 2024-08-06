package club.strifeclient.module;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleInfo {

    String name();
    String renderName() default "";
    String description() default "";
    Category category();
    String[] aliases() default {};
    int keybind() default 0;

}