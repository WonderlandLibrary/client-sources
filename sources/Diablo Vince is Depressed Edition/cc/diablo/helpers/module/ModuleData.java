/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.helpers.module;

import cc.diablo.module.Category;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ModuleData {
    public String name();

    public Category category();

    public String description();

    public int bind() default 0;
}

