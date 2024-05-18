package us.dev.direkt.module.internal.core.autocheat.check;

import us.dev.direkt.module.internal.core.autocheat.anticheat.AntiCheat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Foundry
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckData {
    String label();
    Class<? extends AntiCheat> result();
    int priority() default 10;
}
