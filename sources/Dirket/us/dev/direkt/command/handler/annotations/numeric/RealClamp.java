package us.dev.direkt.command.handler.annotations.numeric;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Foundry
 */
@Target(value={ElementType.PARAMETER})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface RealClamp {
    double min() default Double.NaN;

    double max() default Double.NaN;
}

