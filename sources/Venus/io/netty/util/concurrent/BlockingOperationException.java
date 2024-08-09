/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

public class BlockingOperationException
extends IllegalStateException {
    private static final long serialVersionUID = 2462223247762460301L;

    public BlockingOperationException() {
    }

    public BlockingOperationException(String string) {
        super(string);
    }

    public BlockingOperationException(Throwable throwable) {
        super(throwable);
    }

    public BlockingOperationException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

