/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.lang.Assert;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public abstract class AbstractDeserializer<T>
implements Deserializer<T> {
    protected static final int EOF = -1;
    private static final byte[] EMPTY_BYTES = new byte[0];

    protected AbstractDeserializer() {
    }

    @Override
    public final T deserialize(byte[] byArray) throws DeserializationException {
        byArray = byArray == null ? EMPTY_BYTES : byArray;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
        InputStreamReader inputStreamReader = new InputStreamReader((InputStream)byteArrayInputStream, StandardCharsets.UTF_8);
        return this.deserialize(inputStreamReader);
    }

    @Override
    public final T deserialize(Reader reader) throws DeserializationException {
        Assert.notNull(reader, "Reader argument cannot be null.");
        try {
            return this.doDeserialize(reader);
        } catch (Throwable throwable) {
            if (throwable instanceof DeserializationException) {
                throw (DeserializationException)throwable;
            }
            String string = "Unable to deserialize: " + throwable.getMessage();
            throw new DeserializationException(string, throwable);
        }
    }

    protected abstract T doDeserialize(Reader var1) throws Exception;
}

