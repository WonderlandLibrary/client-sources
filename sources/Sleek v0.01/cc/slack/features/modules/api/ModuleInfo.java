package cc.slack.features.modules.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
   String name();

   String displayName() default "";

   Category category();

   int key() default 0;
}
