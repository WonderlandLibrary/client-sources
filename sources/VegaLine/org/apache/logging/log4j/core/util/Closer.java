/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

public final class Closer {
    private Closer() {
    }

    public static void close(AutoCloseable closeable) throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    public static boolean closeSilently(AutoCloseable closeable) {
        try {
            Closer.close(closeable);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}

