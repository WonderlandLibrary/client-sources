/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonDeserializationContext;
import com.viaversion.viaversion.libs.gson.JsonDeserializer;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonSerializationContext;
import com.viaversion.viaversion.libs.gson.JsonSerializer;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.$Gson$Preconditions;
import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.internal.bind.SerializationDelegatingTypeAdapter;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public final class TreeTypeAdapter<T>
extends SerializationDelegatingTypeAdapter<T> {
    private final JsonSerializer<T> serializer;
    private final JsonDeserializer<T> deserializer;
    final Gson gson;
    private final TypeToken<T> typeToken;
    private final TypeAdapterFactory skipPast;
    private final GsonContextImpl context = new GsonContextImpl(this, null);
    private final boolean nullSafe;
    private volatile TypeAdapter<T> delegate;

    public TreeTypeAdapter(JsonSerializer<T> jsonSerializer, JsonDeserializer<T> jsonDeserializer, Gson gson, TypeToken<T> typeToken, TypeAdapterFactory typeAdapterFactory, boolean bl) {
        this.serializer = jsonSerializer;
        this.deserializer = jsonDeserializer;
        this.gson = gson;
        this.typeToken = typeToken;
        this.skipPast = typeAdapterFactory;
        this.nullSafe = bl;
    }

    public TreeTypeAdapter(JsonSerializer<T> jsonSerializer, JsonDeserializer<T> jsonDeserializer, Gson gson, TypeToken<T> typeToken, TypeAdapterFactory typeAdapterFactory) {
        this(jsonSerializer, jsonDeserializer, gson, typeToken, typeAdapterFactory, true);
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        if (this.deserializer == null) {
            return this.delegate().read(jsonReader);
        }
        JsonElement jsonElement = Streams.parse(jsonReader);
        if (this.nullSafe && jsonElement.isJsonNull()) {
            return null;
        }
        return this.deserializer.deserialize(jsonElement, this.typeToken.getType(), this.context);
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {
        if (this.serializer == null) {
            this.delegate().write(jsonWriter, t);
            return;
        }
        if (this.nullSafe && t == null) {
            jsonWriter.nullValue();
            return;
        }
        JsonElement jsonElement = this.serializer.serialize(t, this.typeToken.getType(), this.context);
        Streams.write(jsonElement, jsonWriter);
    }

    private TypeAdapter<T> delegate() {
        TypeAdapter<T> typeAdapter = this.delegate;
        return typeAdapter != null ? typeAdapter : (this.delegate = this.gson.getDelegateAdapter(this.skipPast, this.typeToken));
    }

    @Override
    public TypeAdapter<T> getSerializationDelegate() {
        return this.serializer != null ? this : this.delegate();
    }

    public static TypeAdapterFactory newFactory(TypeToken<?> typeToken, Object object) {
        return new SingleTypeFactory(object, typeToken, false, null);
    }

    public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken<?> typeToken, Object object) {
        boolean bl = typeToken.getType() == typeToken.getRawType();
        return new SingleTypeFactory(object, typeToken, bl, null);
    }

    public static TypeAdapterFactory newTypeHierarchyFactory(Class<?> clazz, Object object) {
        return new SingleTypeFactory(object, null, false, clazz);
    }

    private final class GsonContextImpl
    implements JsonSerializationContext,
    JsonDeserializationContext {
        final TreeTypeAdapter this$0;

        private GsonContextImpl(TreeTypeAdapter treeTypeAdapter) {
            this.this$0 = treeTypeAdapter;
        }

        @Override
        public JsonElement serialize(Object object) {
            return this.this$0.gson.toJsonTree(object);
        }

        @Override
        public JsonElement serialize(Object object, Type type) {
            return this.this$0.gson.toJsonTree(object, type);
        }

        public <R> R deserialize(JsonElement jsonElement, Type type) throws JsonParseException {
            return (R)this.this$0.gson.fromJson(jsonElement, type);
        }

        GsonContextImpl(TreeTypeAdapter treeTypeAdapter, 1 var2_2) {
            this(treeTypeAdapter);
        }
    }

    private static final class SingleTypeFactory
    implements TypeAdapterFactory {
        private final TypeToken<?> exactType;
        private final boolean matchRawType;
        private final Class<?> hierarchyType;
        private final JsonSerializer<?> serializer;
        private final JsonDeserializer<?> deserializer;

        SingleTypeFactory(Object object, TypeToken<?> typeToken, boolean bl, Class<?> clazz) {
            this.serializer = object instanceof JsonSerializer ? (JsonSerializer)object : null;
            this.deserializer = object instanceof JsonDeserializer ? (JsonDeserializer)object : null;
            $Gson$Preconditions.checkArgument(this.serializer != null || this.deserializer != null);
            this.exactType = typeToken;
            this.matchRawType = bl;
            this.hierarchyType = clazz;
        }

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            boolean bl = this.exactType != null ? this.exactType.equals(typeToken) || this.matchRawType && this.exactType.getType() == typeToken.getRawType() : this.hierarchyType.isAssignableFrom(typeToken.getRawType());
            return bl ? new TreeTypeAdapter(this.serializer, this.deserializer, gson, typeToken, this) : null;
        }
    }
}

