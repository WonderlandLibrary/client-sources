package lol.base.annotations;

import lol.base.addons.CategoryAddon;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleInfo {
    String name();
    String description();
    CategoryAddon category();
    int keyBind() default 0;
    boolean enabled() default false;
    boolean hidden() default false;
}
