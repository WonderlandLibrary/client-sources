/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.io.AbstractSerializer;
import io.jsonwebtoken.io.SerializationException;
import io.jsonwebtoken.io.Serializer;
import io.jsonwebtoken.lang.Assert;
import java.io.OutputStream;
import java.util.Map;

public class NamedSerializer
extends AbstractSerializer<Map<String, ?>> {
    private final String name;
    private final Serializer<Map<String, ?>> DELEGATE;

    public NamedSerializer(String string, Serializer<Map<String, ?>> serializer) {
        this.DELEGATE = Assert.notNull(serializer, "JSON Serializer cannot be null.");
        this.name = Assert.hasText(string, "Name cannot be null or empty.");
    }

    @Override
    protected void doSerialize(Map<String, ?> map, OutputStream outputStream) throws SerializationException {
        try {
            this.DELEGATE.serialize(map, outputStream);
        } catch (Throwable throwable) {
            String string = String.format("Cannot serialize %s to JSON. Cause: %s", this.name, throwable.getMessage());
            throw new SerializationException(string, throwable);
        }
    }

    @Override
    protected void doSerialize(Object object, OutputStream outputStream) throws Exception {
        this.doSerialize((Map)object, outputStream);
    }
}

