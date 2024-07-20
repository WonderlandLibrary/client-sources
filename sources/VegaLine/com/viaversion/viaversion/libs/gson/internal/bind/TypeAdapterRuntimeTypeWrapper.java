/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.bind.SerializationDelegatingTypeAdapter;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class TypeAdapterRuntimeTypeWrapper<T>
extends TypeAdapter<T> {
    private final Gson context;
    private final TypeAdapter<T> delegate;
    private final Type type;

    TypeAdapterRuntimeTypeWrapper(Gson context, TypeAdapter<T> delegate, Type type2) {
        this.context = context;
        this.delegate = delegate;
        this.type = type2;
    }

    @Override
    public T read(JsonReader in) throws IOException {
        return this.delegate.read(in);
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        TypeAdapter<Object> chosen = this.delegate;
        Type runtimeType = TypeAdapterRuntimeTypeWrapper.getRuntimeTypeIfMoreSpecific(this.type, value);
        if (runtimeType != this.type) {
            TypeAdapter<?> runtimeTypeAdapter = this.context.getAdapter(TypeToken.get(runtimeType));
            chosen = !(runtimeTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter) ? runtimeTypeAdapter : (!TypeAdapterRuntimeTypeWrapper.isReflective(this.delegate) ? this.delegate : runtimeTypeAdapter);
        }
        chosen.write(out, value);
    }

    private static boolean isReflective(TypeAdapter<?> typeAdapter) {
        TypeAdapter delegate;
        while (typeAdapter instanceof SerializationDelegatingTypeAdapter && (delegate = ((SerializationDelegatingTypeAdapter)typeAdapter).getSerializationDelegate()) != typeAdapter) {
            typeAdapter = delegate;
        }
        return typeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter;
    }

    private static Type getRuntimeTypeIfMoreSpecific(Type type2, Object value) {
        if (value != null && (type2 instanceof Class || type2 instanceof TypeVariable)) {
            type2 = value.getClass();
        }
        return type2;
    }
}

