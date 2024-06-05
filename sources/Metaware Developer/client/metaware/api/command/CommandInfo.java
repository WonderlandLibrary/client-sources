package client.metaware.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    /*
        The name of the command.
     */
    String name();

    /*
        The description of the command.
    */
    String description() default "";

    /*
        The aliases of the command.
    */
    String[] aliases() default {};

}
