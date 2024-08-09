/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.EncodingException;

public interface Encoder<T, R> {
    public R encode(T var1) throws EncodingException;
}

