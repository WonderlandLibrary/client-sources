/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

public final class Closer {
    private Closer() {
    }

    public static void close(AutoCloseable autoCloseable) throws Exception {
        if (autoCloseable != null) {
            autoCloseable.close();
        }
    }

    public static boolean closeSilently(AutoCloseable autoCloseable) {
        try {
            Closer.close(autoCloseable);
            return true;
        } catch (Exception exception) {
            return true;
        }
    }
}

