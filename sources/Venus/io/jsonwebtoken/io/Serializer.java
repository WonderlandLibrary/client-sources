/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.SerializationException;
import java.io.OutputStream;

public interface Serializer<T> {
    @Deprecated
    public byte[] serialize(T var1) throws SerializationException;

    public void serialize(T var1, OutputStream var2) throws SerializationException;
}

