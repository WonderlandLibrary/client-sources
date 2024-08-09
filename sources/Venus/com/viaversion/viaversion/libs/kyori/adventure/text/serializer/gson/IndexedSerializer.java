/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import java.io.IOException;

final class IndexedSerializer<E>
extends TypeAdapter<E> {
    private final String name;
    private final Index<String, E> map;
    private final boolean throwOnUnknownKey;

    public static <E> TypeAdapter<E> strict(String string, Index<String, E> index) {
        return new IndexedSerializer<E>(string, index, true).nullSafe();
    }

    public static <E> TypeAdapter<E> lenient(String string, Index<String, E> index) {
        return new IndexedSerializer<E>(string, index, false).nullSafe();
    }

    private IndexedSerializer(String string, Index<String, E> index, boolean bl) {
        this.name = string;
        this.map = index;
        this.throwOnUnknownKey = bl;
    }

    @Override
    public void write(JsonWriter jsonWriter, E e) throws IOException {
        jsonWriter.value(this.map.key(e));
    }

    @Override
    public E read(JsonReader jsonReader) throws IOException {
        String string = jsonReader.nextString();
        E e = this.map.value(string);
        if (e != null) {
            return e;
        }
        if (this.throwOnUnknownKey) {
            throw new JsonParseException("invalid " + this.name + ":  " + string);
        }
        return null;
    }
}

