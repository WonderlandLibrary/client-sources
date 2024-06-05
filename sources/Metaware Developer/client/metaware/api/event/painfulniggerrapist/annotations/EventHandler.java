package client.metaware.api.event.painfulniggerrapist.annotations;


import client.metaware.api.event.painfulniggerrapist.Priorities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EventHandler {
    byte value() default Priorities.MEDIUM;
}