package com.google.gson.internal.bind;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.io.*;
import com.google.gson.stream.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public final class TimeTypeAdapter extends TypeAdapter<Time>
{
    public static final TypeAdapterFactory FACTORY;
    private final DateFormat format;
    
    public TimeTypeAdapter() {
        this.format = new SimpleDateFormat("hh:mm:ss a");
    }
    
    @Override
    public synchronized Time read(final JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            final Date date = this.format.parse(in.nextString());
            return new Time(date.getTime());
        }
        catch (ParseException e) {
            throw new JsonSyntaxException(e);
        }
    }
    
    @Override
    public synchronized void write(final JsonWriter out, final Time value) throws IOException {
        out.value((value == null) ? null : this.format.format(value));
    }
    
    static {
        FACTORY = new TypeAdapterFactory() {
            @Override
            public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
                return (TypeAdapter<T>)((typeToken.getRawType() == Time.class) ? new TimeTypeAdapter() : null);
            }
        };
    }
}
