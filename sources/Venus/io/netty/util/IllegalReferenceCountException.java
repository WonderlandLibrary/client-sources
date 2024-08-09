/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

public class IllegalReferenceCountException
extends IllegalStateException {
    private static final long serialVersionUID = -2507492394288153468L;

    public IllegalReferenceCountException() {
    }

    public IllegalReferenceCountException(int n) {
        this("refCnt: " + n);
    }

    public IllegalReferenceCountException(int n, int n2) {
        this("refCnt: " + n + ", " + (n2 > 0 ? "increment: " + n2 : "decrement: " + -n2));
    }

    public IllegalReferenceCountException(String string) {
        super(string);
    }

    public IllegalReferenceCountException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public IllegalReferenceCountException(Throwable throwable) {
        super(throwable);
    }
}

