/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package javax.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Resources;

@Target(value={ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Repeatable(value=Resources.class)
public @interface Resource {
    public String name() default "";

    public String lookup() default "";

    public Class<?> type() default Object.class;

    public AuthenticationType authenticationType() default AuthenticationType.CONTAINER;

    public boolean shareable() default true;

    public String mappedName() default "";

    public String description() default "";

    public static enum AuthenticationType {
        CONTAINER,
        APPLICATION;

    }
}

