/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal.sql;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class SqlTimestampTypeAdapter
extends TypeAdapter<Timestamp> {
    static final TypeAdapterFactory FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Timestamp.class) {
                TypeAdapter<Date> typeAdapter = gson.getAdapter(Date.class);
                return new SqlTimestampTypeAdapter(typeAdapter, null);
            }
            return null;
        }
    };
    private final TypeAdapter<Date> dateTypeAdapter;

    private SqlTimestampTypeAdapter(TypeAdapter<Date> typeAdapter) {
        this.dateTypeAdapter = typeAdapter;
    }

    @Override
    public Timestamp read(JsonReader jsonReader) throws IOException {
        Date date = this.dateTypeAdapter.read(jsonReader);
        return date != null ? new Timestamp(date.getTime()) : null;
    }

    @Override
    public void write(JsonWriter jsonWriter, Timestamp timestamp) throws IOException {
        this.dateTypeAdapter.write(jsonWriter, timestamp);
    }

    @Override
    public Object read(JsonReader jsonReader) throws IOException {
        return this.read(jsonReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, Object object) throws IOException {
        this.write(jsonWriter, (Timestamp)object);
    }

    SqlTimestampTypeAdapter(TypeAdapter typeAdapter, 1 var2_2) {
        this(typeAdapter);
    }
}

