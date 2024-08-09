/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j;

import org.slf4j.LoggerFactory;

public class LoggerFactoryFriend {
    public static void reset() {
        LoggerFactory.reset();
    }

    public static void setDetectLoggerNameMismatch(boolean bl) {
        LoggerFactory.DETECT_LOGGER_NAME_MISMATCH = bl;
    }
}

