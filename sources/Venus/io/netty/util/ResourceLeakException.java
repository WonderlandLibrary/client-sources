/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import java.util.Arrays;

@Deprecated
public class ResourceLeakException
extends RuntimeException {
    private static final long serialVersionUID = 7186453858343358280L;
    private final StackTraceElement[] cachedStackTrace = this.getStackTrace();

    public ResourceLeakException() {
    }

    public ResourceLeakException(String string) {
        super(string);
    }

    public ResourceLeakException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ResourceLeakException(Throwable throwable) {
        super(throwable);
    }

    public int hashCode() {
        StackTraceElement[] stackTraceElementArray = this.cachedStackTrace;
        int n = 0;
        for (StackTraceElement stackTraceElement : stackTraceElementArray) {
            n = n * 31 + stackTraceElement.hashCode();
        }
        return n;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ResourceLeakException)) {
            return true;
        }
        if (object == this) {
            return false;
        }
        return Arrays.equals(this.cachedStackTrace, ((ResourceLeakException)object).cachedStackTrace);
    }
}

