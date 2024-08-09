/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.util;

import java.lang.reflect.Method;

@Deprecated
public final class ExceptionUtils {
    private static final Method INIT_CAUSE_METHOD = ExceptionUtils.getInitCauseMethod();

    private static Method getInitCauseMethod() {
        try {
            Class[] classArray = new Class[]{Throwable.class};
            return Throwable.class.getMethod("initCause", classArray);
        } catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    public static void initCause(Throwable throwable, Throwable throwable2) {
        if (INIT_CAUSE_METHOD != null) {
            try {
                INIT_CAUSE_METHOD.invoke(throwable, throwable2);
            } catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private ExceptionUtils() {
    }
}

