/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Function;

abstract class ReflectionFunction<T, R>
implements Function<T, R> {
    public static final String ERR_MSG = "Reflection operation failed. This is likely due to an internal implementation programming error.  Please report this to the JJWT development team.  Cause: ";

    ReflectionFunction() {
    }

    protected abstract boolean supports(T var1);

    protected abstract R invoke(T var1) throws Throwable;

    @Override
    public final R apply(T t) {
        if (this.supports(t)) {
            try {
                return this.invoke(t);
            } catch (Throwable throwable) {
                String string = ERR_MSG + throwable.getMessage();
                throw new IllegalStateException(string, throwable);
            }
        }
        return null;
    }
}

