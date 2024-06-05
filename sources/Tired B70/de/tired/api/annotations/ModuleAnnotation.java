package de.tired.api.annotations;

import de.tired.module.ModuleCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleAnnotation {

    String name();

    String clickG() default "mod";

    int key() default -1;

    boolean renderPreview() default false;

    String desc() default "";

    ModuleCategory category();

}
