/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util.module;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import pw.vertexcode.nemphis.module.Category;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface ModuleInformation {
    public String name();

    public int color();

    public Category category();
}

