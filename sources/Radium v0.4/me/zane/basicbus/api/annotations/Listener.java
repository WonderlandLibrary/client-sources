package me.zane.basicbus.api.annotations;

import java.lang.annotation.*;

/**
 * @see me.zane.basicbus.api.bus.Bus#subscribe
 * @see me.zane.basicbus.api.bus.Bus#unsubscribe
 * @since 1.4.0
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Listener {

    Priority value() default Priority.MEDIUM;
}
