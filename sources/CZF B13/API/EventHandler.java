/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.API;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface EventHandler {
    byte priority() default 1;
}
