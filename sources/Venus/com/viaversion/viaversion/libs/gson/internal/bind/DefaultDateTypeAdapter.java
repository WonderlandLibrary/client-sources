/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.JavaVersion;
import com.viaversion.viaversion.libs.gson.internal.PreJava9DateFormatProvider;
import com.viaversion.viaversion.libs.gson.internal.bind.TypeAdapters;
import com.viaversion.viaversion.libs.gson.internal.bind.util.ISO8601Utils;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class DefaultDateTypeAdapter<T extends Date>
extends TypeAdapter<T> {
    private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
    private final DateType<T> dateType;
    private final List<DateFormat> dateFormats = new ArrayList<DateFormat>();

    private DefaultDateTypeAdapter(DateType<T> dateType, String string) {
        this.dateType = Objects.requireNonNull(dateType);
        this.dateFormats.add(new SimpleDateFormat(string, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(new SimpleDateFormat(string));
        }
    }

    private DefaultDateTypeAdapter(DateType<T> dateType, int n) {
        this.dateType = Objects.requireNonNull(dateType);
        this.dateFormats.add(DateFormat.getDateInstance(n, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateInstance(n));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateFormat(n));
        }
    }

    private DefaultDateTypeAdapter(DateType<T> dateType, int n, int n2) {
        this.dateType = Objects.requireNonNull(dateType);
        this.dateFormats.add(DateFormat.getDateTimeInstance(n, n2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateTimeInstance(n, n2));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(n, n2));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(JsonWriter jsonWriter, Date date) throws IOException {
        String string;
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        DateFormat dateFormat = this.dateFormats.get(0);
        List<DateFormat> list = this.dateFormats;
        synchronized (list) {
            string = dateFormat.format(date);
        }
        jsonWriter.value(string);
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        Date date = this.deserializeToDate(jsonReader);
        return this.dateType.deserialize(date);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Date deserializeToDate(JsonReader jsonReader) throws IOException {
        String string = jsonReader.nextString();
        List<DateFormat> list = this.dateFormats;
        synchronized (list) {
            for (DateFormat dateFormat : this.dateFormats) {
                try {
                    return dateFormat.parse(string);
                } catch (ParseException parseException) {
                }
            }
        }
        try {
            return ISO8601Utils.parse(string, new ParsePosition(0));
        } catch (ParseException parseException) {
            throw new JsonSyntaxException("Failed parsing '" + string + "' as Date; at path " + jsonReader.getPreviousPath(), parseException);
        }
    }

    public String toString() {
        DateFormat dateFormat = this.dateFormats.get(0);
        if (dateFormat instanceof SimpleDateFormat) {
            return "DefaultDateTypeAdapter(" + ((SimpleDateFormat)dateFormat).toPattern() + ')';
        }
        return "DefaultDateTypeAdapter(" + dateFormat.getClass().getSimpleName() + ')';
    }

    @Override
    public Object read(JsonReader jsonReader) throws IOException {
        return this.read(jsonReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, Object object) throws IOException {
        this.write(jsonWriter, (Date)object);
    }

    DefaultDateTypeAdapter(DateType dateType, String string, 1 var3_3) {
        this(dateType, string);
    }

    DefaultDateTypeAdapter(DateType dateType, int n, 1 var3_3) {
        this(dateType, n);
    }

    DefaultDateTypeAdapter(DateType dateType, int n, int n2, 1 var4_4) {
        this(dateType, n, n2);
    }

    public static abstract class DateType<T extends Date> {
        public static final DateType<Date> DATE = new DateType<Date>(Date.class){

            @Override
            protected Date deserialize(Date date) {
                return date;
            }
        };
        private final Class<T> dateClass;

        protected DateType(Class<T> clazz) {
            this.dateClass = clazz;
        }

        protected abstract T deserialize(Date var1);

        private TypeAdapterFactory createFactory(DefaultDateTypeAdapter<T> defaultDateTypeAdapter) {
            return TypeAdapters.newFactory(this.dateClass, defaultDateTypeAdapter);
        }

        public final TypeAdapterFactory createAdapterFactory(String string) {
            return this.createFactory(new DefaultDateTypeAdapter(this, string, null));
        }

        public final TypeAdapterFactory createAdapterFactory(int n) {
            return this.createFactory(new DefaultDateTypeAdapter(this, n, null));
        }

        public final TypeAdapterFactory createAdapterFactory(int n, int n2) {
            return this.createFactory(new DefaultDateTypeAdapter(this, n, n2, null));
        }

        public final TypeAdapterFactory createDefaultsAdapterFactory() {
            return this.createFactory(new DefaultDateTypeAdapter(this, 2, 2, null));
        }
    }
}

