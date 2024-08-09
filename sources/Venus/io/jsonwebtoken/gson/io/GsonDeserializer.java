/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.gson.io;

import com.google.gson.Gson;
import io.jsonwebtoken.gson.io.GsonSerializer;
import io.jsonwebtoken.io.AbstractDeserializer;
import io.jsonwebtoken.lang.Assert;
import java.io.Reader;

public class GsonDeserializer<T>
extends AbstractDeserializer<T> {
    private final Class<T> returnType;
    protected final Gson gson;

    public GsonDeserializer() {
        this(GsonSerializer.DEFAULT_GSON);
    }

    public GsonDeserializer(Gson gson) {
        this(gson, Object.class);
    }

    private GsonDeserializer(Gson gson, Class<T> clazz) {
        Assert.notNull(gson, "gson cannot be null.");
        Assert.notNull(clazz, "Return type cannot be null.");
        this.gson = gson;
        this.returnType = clazz;
    }

    @Override
    protected T doDeserialize(Reader reader) {
        return this.gson.fromJson(reader, this.returnType);
    }
}

