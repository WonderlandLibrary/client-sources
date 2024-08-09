/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import mpp.venusfr.functions.api.Category;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface FunctionRegister {
    public String name();

    public int key() default 0;

    public Category type();
}

