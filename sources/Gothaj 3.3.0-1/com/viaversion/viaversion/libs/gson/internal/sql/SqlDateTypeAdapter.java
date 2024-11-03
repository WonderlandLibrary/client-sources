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
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

final class SqlDateTypeAdapter extends TypeAdapter<Date> {
   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      @Override
      public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
         return typeToken.getRawType() == Date.class ? new SqlDateTypeAdapter() : null;
      }
   };
   private final DateFormat format = new SimpleDateFormat("MMM d, yyyy");

   private SqlDateTypeAdapter() {
   }

   public Date read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
         in.nextNull();
         return null;
      } else {
         String s = in.nextString();

         try {
            java.util.Date utilDate;
            synchronized (this) {
               utilDate = this.format.parse(s);
            }

            return new Date(utilDate.getTime());
         } catch (ParseException var7) {
            throw new JsonSyntaxException("Failed parsing '" + s + "' as SQL Date; at path " + in.getPreviousPath(), var7);
         }
      }
   }

   public void write(JsonWriter out, Date value) throws IOException {
      if (value == null) {
         out.nullValue();
      } else {
         String dateString;
         synchronized (this) {
            dateString = this.format.format(value);
         }

         out.value(dateString);
      }
   }
}
