/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.exception;

import org.apache.logging.log4j.Logger;

public class RealmsDefaultUncaughtExceptionHandler
implements Thread.UncaughtExceptionHandler {
    private final Logger field_224980_a;

    public RealmsDefaultUncaughtExceptionHandler(Logger logger) {
        this.field_224980_a = logger;
    }

    @Override
    public void uncaughtException(Thread thread2, Throwable throwable) {
        this.field_224980_a.error("Caught previously unhandled exception :");
        this.field_224980_a.error(throwable);
    }
}

