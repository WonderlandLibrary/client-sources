package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.JavaVersion;
import com.viaversion.viaversion.libs.gson.internal.PreJava9DateFormatProvider;
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

public final class DefaultDateTypeAdapter<T extends Date> extends TypeAdapter<T> {
   private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
   private final DefaultDateTypeAdapter.DateType<T> dateType;
   private final List<DateFormat> dateFormats = new ArrayList<>();

   private DefaultDateTypeAdapter(DefaultDateTypeAdapter.DateType<T> dateType, String datePattern) {
      this.dateType = Objects.requireNonNull(dateType);
      this.dateFormats.add(new SimpleDateFormat(datePattern, Locale.US));
      if (!Locale.getDefault().equals(Locale.US)) {
         this.dateFormats.add(new SimpleDateFormat(datePattern));
      }
   }

   private DefaultDateTypeAdapter(DefaultDateTypeAdapter.DateType<T> dateType, int style) {
      this.dateType = Objects.requireNonNull(dateType);
      this.dateFormats.add(DateFormat.getDateInstance(style, Locale.US));
      if (!Locale.getDefault().equals(Locale.US)) {
         this.dateFormats.add(DateFormat.getDateInstance(style));
      }

      if (JavaVersion.isJava9OrLater()) {
         this.dateFormats.add(PreJava9DateFormatProvider.getUSDateFormat(style));
      }
   }

   private DefaultDateTypeAdapter(DefaultDateTypeAdapter.DateType<T> dateType, int dateStyle, int timeStyle) {
      this.dateType = Objects.requireNonNull(dateType);
      this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.US));
      if (!Locale.getDefault().equals(Locale.US)) {
         this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle));
      }

      if (JavaVersion.isJava9OrLater()) {
         this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(dateStyle, timeStyle));
      }
   }

   public void write(JsonWriter out, Date value) throws IOException {
      if (value == null) {
         out.nullValue();
      } else {
         DateFormat dateFormat = this.dateFormats.get(0);
         String dateFormatAsString;
         synchronized (this.dateFormats) {
            dateFormatAsString = dateFormat.format(value);
         }

         out.value(dateFormatAsString);
      }
   }

   public T read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
         in.nextNull();
         return null;
      } else {
         Date date = this.deserializeToDate(in);
         return this.dateType.deserialize(date);
      }
   }

   private Date deserializeToDate(JsonReader in) throws IOException {
      String s = in.nextString();
      synchronized (this.dateFormats) {
         for (DateFormat dateFormat : this.dateFormats) {
            Date var10000;
            try {
               var10000 = dateFormat.parse(s);
            } catch (ParseException var9) {
               continue;
            }

            return var10000;
         }
      }

      try {
         return ISO8601Utils.parse(s, new ParsePosition(0));
      } catch (ParseException var8) {
         throw new JsonSyntaxException("Failed parsing '" + s + "' as Date; at path " + in.getPreviousPath(), var8);
      }
   }

   @Override
   public String toString() {
      DateFormat defaultFormat = this.dateFormats.get(0);
      return defaultFormat instanceof SimpleDateFormat
         ? "DefaultDateTypeAdapter(" + ((SimpleDateFormat)defaultFormat).toPattern() + ')'
         : "DefaultDateTypeAdapter(" + defaultFormat.getClass().getSimpleName() + ')';
   }

   public abstract static class DateType<T extends Date> {
      public static final DefaultDateTypeAdapter.DateType<Date> DATE = new DefaultDateTypeAdapter.DateType<Date>(Date.class) {
         @Override
         protected Date deserialize(Date date) {
            return date;
         }
      };
      private final Class<T> dateClass;

      protected DateType(Class<T> dateClass) {
         this.dateClass = dateClass;
      }

      protected abstract T deserialize(Date var1);

      private TypeAdapterFactory createFactory(DefaultDateTypeAdapter<T> adapter) {
         return TypeAdapters.newFactory(this.dateClass, adapter);
      }

      public final TypeAdapterFactory createAdapterFactory(String datePattern) {
         return this.createFactory(new DefaultDateTypeAdapter<>(this, datePattern));
      }

      public final TypeAdapterFactory createAdapterFactory(int style) {
         return this.createFactory(new DefaultDateTypeAdapter<>(this, style));
      }

      public final TypeAdapterFactory createAdapterFactory(int dateStyle, int timeStyle) {
         return this.createFactory(new DefaultDateTypeAdapter<>(this, dateStyle, timeStyle));
      }

      public final TypeAdapterFactory createDefaultsAdapterFactory() {
         return this.createFactory(new DefaultDateTypeAdapter<>(this, 2, 2));
      }
   }
}
