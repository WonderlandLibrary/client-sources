package us.dev.direkt.module.internal.core.autocheat.anticheat;

import us.dev.direkt.module.internal.core.autocheat.check.Check;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Foundry
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AntiCheatChecks {
    Class<? extends Check>[] value();
}
