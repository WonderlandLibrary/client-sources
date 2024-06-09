package io.github.liticane.clients.feature.event.api.annotations;

import io.github.liticane.clients.feature.event.Priority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubscribeEvent {
    byte value() default Priority.MEDIUM;
}