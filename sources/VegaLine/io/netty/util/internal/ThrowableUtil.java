/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public final class ThrowableUtil {
    private ThrowableUtil() {
    }

    public static <T extends Throwable> T unknownStackTrace(T cause, Class<?> clazz, String method) {
        cause.setStackTrace(new StackTraceElement[]{new StackTraceElement(clazz.getName(), method, null, -1)});
        return cause;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String stackTraceToString(Throwable cause) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        cause.printStackTrace(pout);
        pout.flush();
        try {
            String string = new String(out.toByteArray());
            return string;
        } finally {
            try {
                out.close();
            } catch (IOException iOException) {}
        }
    }
}

