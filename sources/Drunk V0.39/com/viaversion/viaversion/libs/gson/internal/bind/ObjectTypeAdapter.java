/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.LinkedTreeMap;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class ObjectTypeAdapter
extends TypeAdapter<Object> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (type.getRawType() != Object.class) return null;
            return new ObjectTypeAdapter(gson);
        }
    };
    private final Gson gson;

    ObjectTypeAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object read(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        switch (2.$SwitchMap$com$google$gson$stream$JsonToken[token.ordinal()]) {
            case 1: {
                ArrayList<Object> list = new ArrayList<Object>();
                in.beginArray();
                while (true) {
                    if (!in.hasNext()) {
                        in.endArray();
                        return list;
                    }
                    list.add(this.read(in));
                }
            }
            case 2: {
                LinkedTreeMap<String, Object> map = new LinkedTreeMap<String, Object>();
                in.beginObject();
                while (true) {
                    if (!in.hasNext()) {
                        in.endObject();
                        return map;
                    }
                    map.put(in.nextName(), this.read(in));
                }
            }
            case 3: {
                return in.nextString();
            }
            case 4: {
                return in.nextDouble();
            }
            case 5: {
                return in.nextBoolean();
            }
            case 6: {
                in.nextNull();
                return null;
            }
        }
        throw new IllegalStateException();
    }

    @Override
    public void write(JsonWriter out, Object value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        TypeAdapter<?> typeAdapter = this.gson.getAdapter(value.getClass());
        if (typeAdapter instanceof ObjectTypeAdapter) {
            out.beginObject();
            out.endObject();
            return;
        }
        typeAdapter.write(out, value);
    }
}

