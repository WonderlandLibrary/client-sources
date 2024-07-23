package io.github.liticane.monoxide.module.api.data;

import io.github.liticane.monoxide.module.api.ModuleCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleData {
    String name();
    String identifier() default "";
    String description();
    ModuleCategory category();
    String[] supportedIPs() default {""};
    int key() default 0;
    boolean enabled() default false;
    boolean alwaysRegistered() default false;
    boolean frozenState() default false;

}
