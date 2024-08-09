/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.DeserializationException;
import java.io.Reader;

public interface Deserializer<T> {
    @Deprecated
    public T deserialize(byte[] var1) throws DeserializationException;

    public T deserialize(Reader var1) throws DeserializationException;
}

