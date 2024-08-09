/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

public final class Booleans {
    private Booleans() {
    }

    public static boolean parseBoolean(String string, boolean bl) {
        return "true".equalsIgnoreCase(string) || bl && !"false".equalsIgnoreCase(string);
    }
}

