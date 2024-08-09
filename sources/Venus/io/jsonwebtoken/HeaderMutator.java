/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.lang.MapMutator;

public interface HeaderMutator<T extends HeaderMutator<T>>
extends MapMutator<String, Object, T> {
    public T type(String var1);

    public T contentType(String var1);

    @Deprecated
    public T setType(String var1);

    @Deprecated
    public T setContentType(String var1);

    @Deprecated
    public T setCompressionAlgorithm(String var1);
}

