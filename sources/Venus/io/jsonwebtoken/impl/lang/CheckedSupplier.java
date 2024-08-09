/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

public interface CheckedSupplier<T> {
    public T get() throws Exception;
}

