/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
import java.util.Map;

public final class MapTypeAdapterFactory
implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    final boolean complexMapKeySerialization;

    public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor, boolean bl) {
        this.constructorConstructor = constructorConstructor;
        this.complexMapKeySerialization = bl;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<T> clazz = typeToken.getRawType();
        if (!Map.class.isAssignableFrom(clazz)) {
            return null;
        }
        Type[] typeArray = $Gson$Types.getMapKeyAndValueTypes(type, clazz);
        TypeAdapter<?> typeAdapter = this.getKeyAdapter(gson, typeArray[0]);
        TypeAdapter<?> typeAdapter2 = gson.getAdapter(TypeToken.get(typeArray[5]));
        ObjectConstructor<T> objectConstructor = this.constructorConstructor.get(typeToken);
        Adapter adapter = new Adapter(this, gson, typeArray[0], typeAdapter, typeArray[5], typeAdapter2, objectConstructor);
        return adapter;
    }

    private TypeAdapter<?> getKeyAdapter(Gson gson, Type type) {
        return type == Boolean.TYPE || type == Boolean.class ? TypeAdapters.BOOLEAN_AS_STRING : gson.getAdapter(TypeToken.get(type));
    }

    private final class Adapter<K, V>
    extends TypeAdapter<Map<K, V>> {
        private final TypeAdapter<K> keyTypeAdapter;
        private final TypeAdapter<V> valueTypeAdapter;
        private final ObjectConstructor<? extends Map<K, V>> constructor;
        final MapTypeAdapterFactory this$0;

        public Adapter(MapTypeAdapterFactory mapTypeAdapterFactory, Gson gson, Type type, TypeAdapter<K> typeAdapter, Type type2, TypeAdapter<V> typeAdapter2, ObjectConstructor<? extends Map<K, V>> objectConstructor) {
            this.this$0 = mapTypeAdapterFactory;
            this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper<K>(gson, typeAdapter, type);
            this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper<V>(gson, typeAdapter2, type2);
            this.constructor = objectConstructor;
        }

        @Override
        public Map<K, V> read(JsonReader jsonReader) throws IOException {
            JsonToken jsonToken = jsonReader.peek();
            if (jsonToken == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Map<K, V> map = this.constructor.construct();
            if (jsonToken == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginArray();
                    K k = this.keyTypeAdapter.read(jsonReader);
                    V v = this.valueTypeAdapter.read(jsonReader);
                    V v2 = map.put(k, v);
                    if (v2 != null) {
                        throw new JsonSyntaxException("duplicate key: " + k);
                    }
                    jsonReader.endArray();
                }
                jsonReader.endArray();
            } else {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    V v;
                    JsonReaderInternalAccess.INSTANCE.promoteNameToValue(jsonReader);
                    K k = this.keyTypeAdapter.read(jsonReader);
                    V v3 = map.put(k, v = this.valueTypeAdapter.read(jsonReader));
                    if (v3 == null) continue;
                    throw new JsonSyntaxException("duplicate key: " + k);
                }
                jsonReader.endObject();
            }
            return map;
        }

        @Override
        public void write(JsonWriter jsonWriter, Map<K, V> map) throws IOException {
            JsonElement jsonElement;
            if (map == null) {
                jsonWriter.nullValue();
                return;
            }
            if (!this.this$0.complexMapKeySerialization) {
                jsonWriter.beginObject();
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    jsonWriter.name(String.valueOf(entry.getKey()));
                    this.valueTypeAdapter.write(jsonWriter, entry.getValue());
                }
                jsonWriter.endObject();
                return;
            }
            boolean bl = false;
            ArrayList<JsonElement> arrayList = new ArrayList<JsonElement>(map.size());
            ArrayList<V> arrayList2 = new ArrayList<V>(map.size());
            for (Map.Entry<K, V> entry : map.entrySet()) {
                jsonElement = this.keyTypeAdapter.toJsonTree(entry.getKey());
                arrayList.add(jsonElement);
                arrayList2.add(entry.getValue());
                bl |= jsonElement.isJsonArray() || jsonElement.isJsonObject();
            }
            if (bl) {
                jsonWriter.beginArray();
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    jsonWriter.beginArray();
                    Streams.write((JsonElement)arrayList.get(i), jsonWriter);
                    this.valueTypeAdapter.write(jsonWriter, arrayList2.get(i));
                    jsonWriter.endArray();
                }
                jsonWriter.endArray();
            } else {
                jsonWriter.beginObject();
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    jsonElement = (JsonElement)arrayList.get(i);
                    jsonWriter.name(this.keyToString(jsonElement));
                    this.valueTypeAdapter.write(jsonWriter, arrayList2.get(i));
                }
                jsonWriter.endObject();
            }
        }

        private String keyToString(JsonElement jsonElement) {
            if (jsonElement.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
                if (jsonPrimitive.isNumber()) {
                    return String.valueOf(jsonPrimitive.getAsNumber());
                }
                if (jsonPrimitive.isBoolean()) {
                    return Boolean.toString(jsonPrimitive.getAsBoolean());
                }
                if (jsonPrimitive.isString()) {
                    return jsonPrimitive.getAsString();
                }
                throw new AssertionError();
            }
            if (jsonElement.isJsonNull()) {
                return "null";
            }
            throw new AssertionError();
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Map)object);
        }
    }
}

