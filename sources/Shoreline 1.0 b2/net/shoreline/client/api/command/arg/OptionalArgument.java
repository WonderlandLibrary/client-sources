package net.shoreline.client.api.command.arg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Argument
 * @see ArgumentFactory
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OptionalArgument
{

}
