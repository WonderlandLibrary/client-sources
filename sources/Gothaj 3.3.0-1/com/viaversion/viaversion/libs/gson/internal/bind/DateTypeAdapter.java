package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.JavaVersion;
import com.viaversion.viaversion.libs.gson.internal.PreJava9DateFormatProvider;
import com.viaversion.viaversion.libs.gson.internal.bind.util.ISO8601Utils;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class DateTypeAdapter extends TypeAdapter<Date> {
   public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      @Override
      public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
         return typeToken.getRawType() == Date.class ? new DateTypeAdapter() : null;
      }
   };
   private final List<DateFormat> dateFormats = new ArrayList<>();

   public DateTypeAdapter() {
      this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
      if (!Locale.getDefault().equals(Locale.US)) {
         this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
      }

      if (JavaVersion.isJava9OrLater()) {
         this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
      }
   }

   public Date read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
         in.nextNull();
         return null;
      } else {
         return this.deserializeToDate(in);
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
}
