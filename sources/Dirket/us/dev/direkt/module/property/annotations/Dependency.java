package us.dev.direkt.module.property.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author Foundry
 */
@Target(ElementType.ANNOTATION_TYPE)
public @interface Dependency {
    Class<?> type();
    String label();
    String value();
}
