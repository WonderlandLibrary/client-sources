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

    TypeAdapterRuntimeTypeWrapper(Gson gson, TypeAdapter<T> typeAdapter, Type type) {
        this.context = gson;
        this.delegate = typeAdapter;
        this.type = type;
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        return this.delegate.read(jsonReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {
        TypeAdapter<Object> typeAdapter = this.delegate;
        Type type = TypeAdapterRuntimeTypeWrapper.getRuntimeTypeIfMoreSpecific(this.type, t);
        if (type != this.type) {
            TypeAdapter<?> typeAdapter2 = this.context.getAdapter(TypeToken.get(type));
            typeAdapter = !(typeAdapter2 instanceof ReflectiveTypeAdapterFactory.Adapter) ? typeAdapter2 : (!TypeAdapterRuntimeTypeWrapper.isReflective(this.delegate) ? this.delegate : typeAdapter2);
        }
        typeAdapter.write(jsonWriter, t);
    }

    private static boolean isReflective(TypeAdapter<?> typeAdapter) {
        TypeAdapter typeAdapter2;
        while (typeAdapter instanceof SerializationDelegatingTypeAdapter && (typeAdapter2 = ((SerializationDelegatingTypeAdapter)typeAdapter).getSerializationDelegate()) != typeAdapter) {
            typeAdapter = typeAdapter2;
        }
        return typeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter;
    }

    private static Type getRuntimeTypeIfMoreSpecific(Type clazz, Object object) {
        if (object != null && (clazz instanceof Class || clazz instanceof TypeVariable)) {
            clazz = object.getClass();
        }
        return clazz;
    }
}

