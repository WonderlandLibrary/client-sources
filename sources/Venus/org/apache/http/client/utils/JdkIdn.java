/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.utils.Idn;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class JdkIdn
implements Idn {
    private final Method toUnicode;

    public JdkIdn() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("java.net.IDN");
        try {
            this.toUnicode = clazz.getMethod("toUnicode", String.class);
        } catch (SecurityException securityException) {
            throw new IllegalStateException(securityException.getMessage(), securityException);
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new IllegalStateException(noSuchMethodException.getMessage(), noSuchMethodException);
        }
    }

    @Override
    public String toUnicode(String string) {
        try {
            return (String)this.toUnicode.invoke(null, string);
        } catch (IllegalAccessException illegalAccessException) {
            throw new IllegalStateException(illegalAccessException.getMessage(), illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getCause();
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }
}

