package net.minecraft.util;

import com.google.gson.reflect.*;
import com.google.gson.*;
import com.google.common.collect.*;
import java.io.*;
import com.google.gson.stream.*;
import java.util.*;

public class EnumTypeAdapterFactory implements TypeAdapterFactory
{
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
        final Class rawType = typeToken.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }
        final HashMap hashMap = Maps.newHashMap();
        final Object[] enumConstants;
        final int length = (enumConstants = rawType.getEnumConstants()).length;
        int i = "".length();
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (i < length) {
            final Object o = enumConstants[i];
            hashMap.put(this.func_151232_a(o), o);
            ++i;
        }
        return new TypeAdapter<T>(this, hashMap) {
            final EnumTypeAdapterFactory this$0;
            private final Map val$map;
            
            public T read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return this.val$map.get(jsonReader.nextString());
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public void write(final JsonWriter jsonWriter, final T t) throws IOException {
                if (t == null) {
                    jsonWriter.nullValue();
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                }
                else {
                    jsonWriter.value(EnumTypeAdapterFactory.access$0(this.this$0, t));
                }
            }
        };
    }
    
    static String access$0(final EnumTypeAdapterFactory enumTypeAdapterFactory, final Object o) {
        return enumTypeAdapterFactory.func_151232_a(o);
    }
    
    private String func_151232_a(final Object o) {
        String s;
        if (o instanceof Enum) {
            s = ((Enum)o).name().toLowerCase(Locale.US);
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            s = o.toString().toLowerCase(Locale.US);
        }
        return s;
    }
}
