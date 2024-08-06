package com.shroomclient.shroomclientnextgen.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegisterModule {
    String name() default "";

    String uniqueId();

    String description();

    String[] tags() default {};

    ModuleCategory category();

    boolean enabledByDefault() default false;
}
