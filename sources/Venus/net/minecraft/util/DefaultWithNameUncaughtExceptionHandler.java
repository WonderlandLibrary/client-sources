/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import org.apache.logging.log4j.Logger;

public class DefaultWithNameUncaughtExceptionHandler
implements Thread.UncaughtExceptionHandler {
    private final Logger logger;

    public DefaultWithNameUncaughtExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void uncaughtException(Thread thread2, Throwable throwable) {
        this.logger.error("Caught previously unhandled exception :");
        this.logger.error(thread2.getName(), throwable);
    }
}

