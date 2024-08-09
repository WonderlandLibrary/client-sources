/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SuppressJava6Requirement;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public final class ThrowableUtil {
    private ThrowableUtil() {
    }

    public static <T extends Throwable> T unknownStackTrace(T t, Class<?> clazz, String string) {
        t.setStackTrace(new StackTraceElement[]{new StackTraceElement(clazz.getName(), string, null, -1)});
        return t;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String stackTraceToString(Throwable throwable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        throwable.printStackTrace(printStream);
        printStream.flush();
        try {
            String string = new String(byteArrayOutputStream.toByteArray());
            return string;
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException iOException) {}
        }
    }

    public static boolean haveSuppressed() {
        return PlatformDependent.javaVersion() >= 7;
    }

    @SuppressJava6Requirement(reason="Throwable addSuppressed is only available for >= 7. Has check for < 7.")
    public static void addSuppressed(Throwable throwable, Throwable throwable2) {
        if (!ThrowableUtil.haveSuppressed()) {
            return;
        }
        throwable.addSuppressed(throwable2);
    }

    public static void addSuppressedAndClear(Throwable throwable, List<Throwable> list) {
        ThrowableUtil.addSuppressed(throwable, list);
        list.clear();
    }

    public static void addSuppressed(Throwable throwable, List<Throwable> list) {
        for (Throwable throwable2 : list) {
            ThrowableUtil.addSuppressed(throwable, throwable2);
        }
    }
}

