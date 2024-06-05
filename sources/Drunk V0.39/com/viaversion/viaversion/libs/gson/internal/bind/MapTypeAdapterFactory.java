/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.$Gson$Types;
import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
import com.viaversion.viaversion.libs.gson.internal.JsonReaderInternalAccess;
import com.viaversion.viaversion.libs.gson.internal.ObjectConstructor;
import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.internal.bind.TypeAdapterRuntimeTypeWrapper;
import com.viaversion.viaversion.libs.gson.internal.bind.TypeAdapters;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class MapTypeAdapterFactory
implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    final boolean complexMapKeySerialization;

    public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor, boolean complexMapKeySerialization) {
        this.constructorConstructor = constructorConstructor;
        this.complexMapKeySerialization = complexMapKeySerialization;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<T> rawType = typeToken.getRawType();
        if (!Map.class.isAssignableFrom(rawType)) {
            return null;
        }
        Class<?> rawTypeOfSrc = $Gson$Types.getRawType(type);
        Type[] keyAndValueTypes = $Gson$Types.getMapKeyAndValueTypes(type, rawTypeOfSrc);
        TypeAdapter<?> keyAdapter = this.getKeyAdapter(gson, keyAndValueTypes[0]);
        TypeAdapter<?> valueAdapter = gson.getAdapter(TypeToken.get(keyAndValueTypes[1]));
        ObjectConstructor<T> constructor = this.constructorConstructor.get(typeToken);
        return new Adapter(gson, keyAndValueTypes[0], keyAdapter, keyAndValueTypes[1], valueAdapter, constructor);
    }

    private TypeAdapter<?> getKeyAdapter(Gson context, Type keyType) {
        TypeAdapter<Boolean> typeAdapter;
        if (keyType != Boolean.TYPE && keyType != Boolean.class) {
            typeAdapter = context.getAdapter(TypeToken.get(keyType));
            return typeAdapter;
        }
        typeAdapter = TypeAdapters.BOOLEAN_AS_STRING;
        return typeAdapter;
    }

    private final class Adapter<K, V>
    extends TypeAdapter<Map<K, V>> {
        private final TypeAdapter<K> keyTypeAdapter;
        private final TypeAdapter<V> valueTypeAdapter;
        private final ObjectConstructor<? extends Map<K, V>> constructor;

        public Adapter(Gson context, Type keyType, TypeAdapter<K> keyTypeAdapter, Type valueType, TypeAdapter<V> valueTypeAdapter, ObjectConstructor<? extends Map<K, V>> constructor) {
            this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper<K>(context, keyTypeAdapter, keyType);
            this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper<V>(context, valueTypeAdapter, valueType);
            this.constructor = constructor;
        }

        @Override
        public Map<K, V> read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            if (peek == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            Map<K, V> map = this.constructor.construct();
            if (peek != JsonToken.BEGIN_ARRAY) {
                V value;
                K key;
                V replaced;
                in.beginObject();
                do {
                    if (!in.hasNext()) {
                        in.endObject();
                        return map;
                    }
                    JsonReaderInternalAccess.INSTANCE.promoteNameToValue(in);
                } while ((replaced = map.put(key = this.keyTypeAdapter.read(in), value = this.valueTypeAdapter.read(in))) == null);
                throw new JsonSyntaxException("duplicate key: " + key);
            }
            in.beginArray();
            while (true) {
                if (!in.hasNext()) {
                    in.endArray();
                    return map;
                }
                in.beginArray();
                K key = this.keyTypeAdapter.read(in);
                V value = this.valueTypeAdapter.read(in);
                V replaced = map.put(key, value);
                if (replaced != null) {
                    throw new JsonSyntaxException("duplicate key: " + key);
                }
                in.endArray();
            }
        }

        @Override
        public void write(JsonWriter out, Map<K, V> map) throws IOException {
            JsonElement keyElement;
            if (map == null) {
                out.nullValue();
                return;
            }
            if (!MapTypeAdapterFactory.this.complexMapKeySerialization) {
                out.beginObject();
                Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                while (true) {
                    if (!iterator.hasNext()) {
                        out.endObject();
                        return;
                    }
                    Map.Entry<K, V> entry = iterator.next();
                    out.name(String.valueOf(entry.getKey()));
                    this.valueTypeAdapter.write(out, entry.getValue());
                }
            }
            boolean hasComplexKeys = false;
            ArrayList<JsonElement> keys = new ArrayList<JsonElement>(map.size());
            ArrayList<V> values = new ArrayList<V>(map.size());
            for (Map.Entry<K, V> entry : map.entrySet()) {
                keyElement = this.keyTypeAdapter.toJsonTree(entry.getKey());
                keys.add(keyElement);
                values.add(entry.getValue());
                hasComplexKeys |= keyElement.isJsonArray() || keyElement.isJsonObject();
            }
            if (hasComplexKeys) {
                out.beginArray();
                int i = 0;
                int size = keys.size();
                while (true) {
                    if (i >= size) {
                        out.endArray();
                        return;
                    }
                    out.beginArray();
                    Streams.write((JsonElement)keys.get(i), out);
                    this.valueTypeAdapter.write(out, values.get(i));
                    out.endArray();
                    ++i;
                }
            }
            out.beginObject();
            int i = 0;
            int size = keys.size();
            while (true) {
                if (i >= size) {
                    out.endObject();
                    return;
                }
                keyElement = (JsonElement)keys.get(i);
                out.name(this.keyToString(keyElement));
                this.valueTypeAdapter.write(out, values.get(i));
                ++i;
            }
        }

        private String keyToString(JsonElement keyElement) {
            if (!keyElement.isJsonPrimitive()) {
                if (!keyElement.isJsonNull()) throw new AssertionError();
                return "null";
            }
            JsonPrimitive primitive = keyElement.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return String.valueOf(primitive.getAsNumber());
            }
            if (primitive.isBoolean()) {
                return Boolean.toString(primitive.getAsBoolean());
            }
            if (!primitive.isString()) throw new AssertionError();
            return primitive.getAsString();
        }
    }
}

