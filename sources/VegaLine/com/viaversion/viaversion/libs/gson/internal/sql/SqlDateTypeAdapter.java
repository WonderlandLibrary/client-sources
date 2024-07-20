/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal.sql;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

final class SqlDateTypeAdapter
extends TypeAdapter<java.sql.Date> {
    static final TypeAdapterFactory FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            return typeToken.getRawType() == java.sql.Date.class ? new SqlDateTypeAdapter() : null;
        }
    };
    private final DateFormat format = new SimpleDateFormat("MMM d, yyyy");

    private SqlDateTypeAdapter() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public java.sql.Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String s = in.nextString();
        try {
            Date utilDate;
            SqlDateTypeAdapter sqlDateTypeAdapter = this;
            synchronized (sqlDateTypeAdapter) {
                utilDate = this.format.parse(s);
            }
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new JsonSyntaxException("Failed parsing '" + s + "' as SQL Date; at path " + in.getPreviousPath(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(JsonWriter out, java.sql.Date value) throws IOException {
        String dateString;
        if (value == null) {
            out.nullValue();
            return;
        }
        SqlDateTypeAdapter sqlDateTypeAdapter = this;
        synchronized (sqlDateTypeAdapter) {
            dateString = this.format.format(value);
        }
        out.value(dateString);
    }
}

