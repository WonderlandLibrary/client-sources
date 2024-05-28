package dev.vertic.event.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventLink {

    byte value() default Priority.NORMAL;

}
