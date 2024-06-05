/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import digital.rbq.module.ModuleCategory;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface Category {
    public ModuleCategory value();
}

