/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

public final class ThreadQuickExitException
extends RuntimeException {
    public static final ThreadQuickExitException INSTANCE = new ThreadQuickExitException();

    private ThreadQuickExitException() {
        this.setStackTrace(new StackTraceElement[0]);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        this.setStackTrace(new StackTraceElement[0]);
        return this;
    }
}

