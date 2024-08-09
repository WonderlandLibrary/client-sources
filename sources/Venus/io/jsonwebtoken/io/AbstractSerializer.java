/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.SerializationException;
import io.jsonwebtoken.io.Serializer;
import io.jsonwebtoken.lang.Objects;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public abstract class AbstractSerializer<T>
implements Serializer<T> {
    protected AbstractSerializer() {
    }

    @Override
    public final byte[] serialize(T t) throws SerializationException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.serialize(t, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public final void serialize(T t, OutputStream outputStream) throws SerializationException {
        try {
            this.doSerialize(t, outputStream);
        } catch (Throwable throwable) {
            if (throwable instanceof SerializationException) {
                throw (SerializationException)throwable;
            }
            String string = "Unable to serialize object of type " + Objects.nullSafeClassName(t) + ": " + throwable.getMessage();
            throw new SerializationException(string, throwable);
        }
    }

    protected abstract void doSerialize(T var1, OutputStream var2) throws Exception;
}

