package com.google.gson.internal.bind;

import com.google.gson.*;
import com.google.gson.internal.*;
import java.util.*;
import java.io.*;
import com.google.gson.stream.*;
import com.google.gson.reflect.*;

public final class ObjectTypeAdapter extends TypeAdapter<Object>
{
    public static final TypeAdapterFactory FACTORY;
    private final Gson gson;
    
    ObjectTypeAdapter(final Gson gson) {
        this.gson = gson;
    }
    
    @Override
    public Object read(final JsonReader in) throws IOException {
        final JsonToken token = in.peek();
        switch (token) {
            case BEGIN_ARRAY: {
                final List<Object> list = new ArrayList<Object>();
                in.beginArray();
                while (in.hasNext()) {
                    list.add(this.read(in));
                }
                in.endArray();
                return list;
            }
            case BEGIN_OBJECT: {
                final Map<String, Object> map = new LinkedTreeMap<String, Object>();
                in.beginObject();
                while (in.hasNext()) {
                    map.put(in.nextName(), this.read(in));
                }
                in.endObject();
                return map;
            }
            case STRING: {
                return in.nextString();
            }
            case NUMBER: {
                return in.nextDouble();
            }
            case BOOLEAN: {
                return in.nextBoolean();
            }
            case NULL: {
                in.nextNull();
                return null;
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
    
    @Override
    public void write(final JsonWriter out, final Object value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        final TypeAdapter<Object> typeAdapter = this.gson.getAdapter(value.getClass());
        if (typeAdapter instanceof ObjectTypeAdapter) {
            out.beginObject();
            out.endObject();
            return;
        }
        typeAdapter.write(out, value);
    }
    
    static {
        FACTORY = new TypeAdapterFactory() {
            @Override
            public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
                if (type.getRawType() == Object.class) {
                    return (TypeAdapter<T>)new ObjectTypeAdapter(gson);
                }
                return null;
            }
        };
    }
}
