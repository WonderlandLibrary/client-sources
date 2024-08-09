/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeReader;
import com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeWriter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class TypeAdapter<T> {
    public abstract void write(JsonWriter var1, T var2) throws IOException;

    public final void toJson(Writer writer, T t) throws IOException {
        JsonWriter jsonWriter = new JsonWriter(writer);
        this.write(jsonWriter, t);
    }

    public final TypeAdapter<T> nullSafe() {
        return new TypeAdapter<T>(this){
            final TypeAdapter this$0;
            {
                this.this$0 = typeAdapter;
            }

            @Override
            public void write(JsonWriter jsonWriter, T t) throws IOException {
                if (t == null) {
                    jsonWriter.nullValue();
                } else {
                    this.this$0.write(jsonWriter, t);
                }
            }

            @Override
            public T read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return this.this$0.read(jsonReader);
            }
        };
    }

    public final String toJson(T t) {
        StringWriter stringWriter = new StringWriter();
        try {
            this.toJson(stringWriter, t);
        } catch (IOException iOException) {
            throw new JsonIOException(iOException);
        }
        return stringWriter.toString();
    }

    public final JsonElement toJsonTree(T t) {
        try {
            JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
            this.write(jsonTreeWriter, t);
            return jsonTreeWriter.get();
        } catch (IOException iOException) {
            throw new JsonIOException(iOException);
        }
    }

    public abstract T read(JsonReader var1) throws IOException;

    public final T fromJson(Reader reader) throws IOException {
        JsonReader jsonReader = new JsonReader(reader);
        return this.read(jsonReader);
    }

    public final T fromJson(String string) throws IOException {
        return this.fromJson(new StringReader(string));
    }

    public final T fromJsonTree(JsonElement jsonElement) {
        try {
            JsonTreeReader jsonTreeReader = new JsonTreeReader(jsonElement);
            return this.read(jsonTreeReader);
        } catch (IOException iOException) {
            throw new JsonIOException(iOException);
        }
    }
}

