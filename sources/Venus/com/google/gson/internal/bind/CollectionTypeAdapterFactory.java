/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public final class CollectionTypeAdapterFactory
implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<T> clazz = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(clazz)) {
            return null;
        }
        Type type2 = $Gson$Types.getCollectionElementType(type, clazz);
        TypeAdapter<?> typeAdapter = gson.getAdapter(TypeToken.get(type2));
        ObjectConstructor<T> objectConstructor = this.constructorConstructor.get(typeToken);
        Adapter adapter = new Adapter(gson, type2, typeAdapter, objectConstructor);
        return adapter;
    }

    private static final class Adapter<E>
    extends TypeAdapter<Collection<E>> {
        private final TypeAdapter<E> elementTypeAdapter;
        private final ObjectConstructor<? extends Collection<E>> constructor;

        public Adapter(Gson gson, Type type, TypeAdapter<E> typeAdapter, ObjectConstructor<? extends Collection<E>> objectConstructor) {
            this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(gson, typeAdapter, type);
            this.constructor = objectConstructor;
        }

        @Override
        public Collection<E> read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Collection<E> collection = this.constructor.construct();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                E e = this.elementTypeAdapter.read(jsonReader);
                collection.add(e);
            }
            jsonReader.endArray();
            return collection;
        }

        @Override
        public void write(JsonWriter jsonWriter, Collection<E> collection) throws IOException {
            if (collection == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginArray();
            for (E e : collection) {
                this.elementTypeAdapter.write(jsonWriter, e);
            }
            jsonWriter.endArray();
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Collection)object);
        }
    }
}

