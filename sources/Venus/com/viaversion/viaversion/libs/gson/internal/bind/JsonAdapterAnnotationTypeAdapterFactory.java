/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonDeserializer;
import com.viaversion.viaversion.libs.gson.JsonSerializer;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.annotations.JsonAdapter;
import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
import com.viaversion.viaversion.libs.gson.internal.bind.TreeTypeAdapter;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;

public final class JsonAdapterAnnotationTypeAdapterFactory
implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<T> clazz = typeToken.getRawType();
        JsonAdapter jsonAdapter = clazz.getAnnotation(JsonAdapter.class);
        if (jsonAdapter == null) {
            return null;
        }
        return this.getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter);
    }

    TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor, Gson gson, TypeToken<?> typeToken, JsonAdapter jsonAdapter) {
        TypeAdapter<?> typeAdapter;
        Object obj = constructorConstructor.get(TypeToken.get(jsonAdapter.value())).construct();
        boolean bl = jsonAdapter.nullSafe();
        if (obj instanceof TypeAdapter) {
            typeAdapter = (TypeAdapter<?>)obj;
        } else if (obj instanceof TypeAdapterFactory) {
            typeAdapter = ((TypeAdapterFactory)obj).create(gson, typeToken);
        } else if (obj instanceof JsonSerializer || obj instanceof JsonDeserializer) {
            JsonSerializer jsonSerializer = obj instanceof JsonSerializer ? (JsonSerializer)obj : null;
            JsonDeserializer jsonDeserializer = obj instanceof JsonDeserializer ? (JsonDeserializer)obj : null;
            TreeTypeAdapter treeTypeAdapter = new TreeTypeAdapter(jsonSerializer, jsonDeserializer, gson, typeToken, null, bl);
            typeAdapter = treeTypeAdapter;
            bl = false;
        } else {
            throw new IllegalArgumentException("Invalid attempt to bind an instance of " + obj.getClass().getName() + " as a @JsonAdapter for " + typeToken.toString() + ". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer.");
        }
        if (typeAdapter != null && bl) {
            typeAdapter = typeAdapter.nullSafe();
        }
        return typeAdapter;
    }
}

