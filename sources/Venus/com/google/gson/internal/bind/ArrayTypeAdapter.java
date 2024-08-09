/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class ArrayTypeAdapter<E>
extends TypeAdapter<Object> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            Type type = typeToken.getType();
            if (!(type instanceof GenericArrayType || type instanceof Class && ((Class)type).isArray())) {
                return null;
            }
            Type type2 = $Gson$Types.getArrayComponentType(type);
            TypeAdapter<?> typeAdapter = gson.getAdapter(TypeToken.get(type2));
            ArrayTypeAdapter arrayTypeAdapter = new ArrayTypeAdapter(gson, typeAdapter, $Gson$Types.getRawType(type2));
            return arrayTypeAdapter;
        }
    };
    private final Class<E> componentType;
    private final TypeAdapter<E> componentTypeAdapter;

    public ArrayTypeAdapter(Gson gson, TypeAdapter<E> typeAdapter, Class<E> clazz) {
        this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(gson, typeAdapter, clazz);
        this.componentType = clazz;
    }

    @Override
    public Object read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        ArrayList<E> arrayList = new ArrayList<E>();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            E e = this.componentTypeAdapter.read(jsonReader);
            arrayList.add(e);
        }
        jsonReader.endArray();
        int n = arrayList.size();
        if (this.componentType.isPrimitive()) {
            Object object = Array.newInstance(this.componentType, n);
            for (int i = 0; i < n; ++i) {
                Array.set(object, i, arrayList.get(i));
            }
            return object;
        }
        Object[] objectArray = (Object[])Array.newInstance(this.componentType, n);
        return arrayList.toArray(objectArray);
    }

    @Override
    public void write(JsonWriter jsonWriter, Object object) throws IOException {
        if (object == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.beginArray();
        int n = Array.getLength(object);
        for (int i = 0; i < n; ++i) {
            Object object2 = Array.get(object, i);
            this.componentTypeAdapter.write(jsonWriter, object2);
        }
        jsonWriter.endArray();
    }
}

