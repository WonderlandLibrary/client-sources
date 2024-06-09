package us.dev.direkt.module.annotations;

import us.dev.direkt.module.ModCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModData {
	String label();
	String[] aliases() default {};
    int color() default Integer.MIN_VALUE;
	ModCategory category();
}
