package arsenic.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    String name();

    int minArgs() default 0;

    String help() default "No help provided for this command";

    String[] args() default {};

    String[] aliases() default {};

}
