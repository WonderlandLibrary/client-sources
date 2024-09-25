/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.gson.internal.bind;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import us.myles.viaversion.libs.gson.Gson;
import us.myles.viaversion.libs.gson.JsonSyntaxException;
import us.myles.viaversion.libs.gson.TypeAdapter;
import us.myles.viaversion.libs.gson.TypeAdapterFactory;
import us.myles.viaversion.libs.gson.internal.JavaVersion;
import us.myles.viaversion.libs.gson.internal.PreJava9DateFormatProvider;
import us.myles.viaversion.libs.gson.internal.bind.util.ISO8601Utils;
import us.myles.viaversion.libs.gson.reflect.TypeToken;
import us.myles.viaversion.libs.gson.stream.JsonReader;
import us.myles.viaversion.libs.gson.stream.JsonToken;
import us.myles.viaversion.libs.gson.stream.JsonWriter;

public final class DateTypeAdapter
extends TypeAdapter<Date> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            return typeToken.getRawType() == Date.class ? new DateTypeAdapter() : null;
        }
    };
    private final List<DateFormat> dateFormats = new ArrayList<DateFormat>();

    public DateTypeAdapter() {
        this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return this.deserializeToDate(in.nextString());
    }

    private synchronized Date deserializeToDate(String json) {
        for (DateFormat dateFormat : this.dateFormats) {
            try {
                return dateFormat.parse(json);
            }
            catch (ParseException parseException) {
            }
        }
        try {
            return ISO8601Utils.parse(json, new ParsePosition(0));
        }
        catch (ParseException e) {
            throw new JsonSyntaxException(json, e);
        }
    }

    @Override
    public synchronized void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        String dateFormatAsString = this.dateFormats.get(0).format(value);
        out.value(dateFormatAsString);
    }
}

