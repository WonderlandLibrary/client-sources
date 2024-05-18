package us.dev.direkt.command.handler.annotations.strings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Foundry
 */
@Target(value={ElementType.PARAMETER})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Length {
    int min() default 0;

    int max() default Integer.MAX_VALUE;
}

