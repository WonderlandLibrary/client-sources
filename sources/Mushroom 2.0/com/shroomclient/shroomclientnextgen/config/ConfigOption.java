package com.shroomclient.shroomclientnextgen.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigOption {
    String name();

    String description();

    double min() default 0;

    double max() default 0;

    int precision() default 0;

    // this is a double because i like decimals
    double order() default 0;
}
