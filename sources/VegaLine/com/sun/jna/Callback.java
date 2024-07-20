/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public interface Callback {
    public static final String METHOD_NAME = "callback";
    public static final List<String> FORBIDDEN_NAMES = Collections.unmodifiableList(Arrays.asList("hashCode", "equals", "toString"));

    /*
     * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
     */
    public static interface UncaughtExceptionHandler {
        public void uncaughtException(Callback var1, Throwable var2);
    }
}

