/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import java.io.IOException;

final class KeySerializer
extends TypeAdapter<Key> {
    static final TypeAdapter<Key> INSTANCE = new KeySerializer().nullSafe();

    private KeySerializer() {
    }

    @Override
    public void write(JsonWriter out, Key value) throws IOException {
        out.value(value.asString());
    }

    @Override
    public Key read(JsonReader in) throws IOException {
        return Key.key(in.nextString());
    }
}

