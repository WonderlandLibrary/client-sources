/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.concurrent;

public class CircuitBreakingException
extends RuntimeException {
    private static final long serialVersionUID = 1408176654686913340L;

    public CircuitBreakingException() {
    }

    public CircuitBreakingException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CircuitBreakingException(String string) {
        super(string);
    }

    public CircuitBreakingException(Throwable throwable) {
        super(throwable);
    }
}

