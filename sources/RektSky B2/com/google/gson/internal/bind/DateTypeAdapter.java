package com.google.gson.internal.bind;

import com.google.gson.internal.*;
import java.io.*;
import java.text.*;
import com.google.gson.internal.bind.util.*;
import java.util.*;
import com.google.gson.stream.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public final class DateTypeAdapter extends TypeAdapter<Date>
{
    public static final TypeAdapterFactory FACTORY;
    private final List<DateFormat> dateFormats;
    
    public DateTypeAdapter() {
        (this.dateFormats = new ArrayList<DateFormat>()).add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
        }
    }
    
    @Override
    public Date read(final JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return this.deserializeToDate(in.nextString());
    }
    
    private synchronized Date deserializeToDate(final String json) {
        for (final DateFormat dateFormat : this.dateFormats) {
            try {
                return dateFormat.parse(json);
            }
            catch (ParseException ex) {
                continue;
            }
            break;
        }
        try {
            return ISO8601Utils.parse(json, new ParsePosition(0));
        }
        catch (ParseException e) {
            throw new JsonSyntaxException(json, e);
        }
    }
    
    @Override
    public synchronized void write(final JsonWriter out, final Date value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        final String dateFormatAsString = this.dateFormats.get(0).format(value);
        out.value(dateFormatAsString);
    }
    
    static {
        FACTORY = new TypeAdapterFactory() {
            @Override
            public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
                return (TypeAdapter<T>)((typeToken.getRawType() == Date.class) ? new DateTypeAdapter() : null);
            }
        };
    }
}
