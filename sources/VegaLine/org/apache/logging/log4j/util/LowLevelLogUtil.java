/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Objects;

final class LowLevelLogUtil {
    private static PrintWriter writer = new PrintWriter(System.err, true);

    public static void logException(Throwable exception) {
        exception.printStackTrace(writer);
    }

    public static void logException(String message, Throwable exception) {
        if (message != null) {
            writer.println(message);
        }
        LowLevelLogUtil.logException(exception);
    }

    public static void setOutputStream(OutputStream out) {
        writer = new PrintWriter(Objects.requireNonNull(out), true);
    }

    public static void setWriter(Writer writer) {
        LowLevelLogUtil.writer = new PrintWriter(Objects.requireNonNull(writer), true);
    }

    private LowLevelLogUtil() {
    }
}

