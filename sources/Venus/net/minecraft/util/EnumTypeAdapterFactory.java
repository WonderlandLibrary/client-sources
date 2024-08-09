/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class EnumTypeAdapterFactory
implements TypeAdapterFactory {
    @Override
    @Nullable
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<T> clazz = typeToken.getRawType();
        if (!clazz.isEnum()) {
            return null;
        }
        HashMap<String, T> hashMap = Maps.newHashMap();
        for (T t : clazz.getEnumConstants()) {
            hashMap.put(this.getName(t), t);
        }
        return new TypeAdapter<T>(this, hashMap){
            final Map val$map;
            final EnumTypeAdapterFactory this$0;
            {
                this.this$0 = enumTypeAdapterFactory;
                this.val$map = map;
            }

            @Override
            public void write(JsonWriter jsonWriter, T t) throws IOException {
                if (t == null) {
                    jsonWriter.nullValue();
                } else {
                    jsonWriter.value(this.this$0.getName(t));
                }
            }

            @Override
            @Nullable
            public T read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return this.val$map.get(jsonReader.nextString());
            }
        };
    }

    private String getName(Object object) {
        return object instanceof Enum ? ((Enum)object).name().toLowerCase(Locale.ROOT) : object.toString().toLowerCase(Locale.ROOT);
    }
}

