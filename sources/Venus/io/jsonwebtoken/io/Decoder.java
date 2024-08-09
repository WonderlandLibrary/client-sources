/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.DecodingException;

public interface Decoder<T, R> {
    public R decode(T var1) throws DecodingException;
}

