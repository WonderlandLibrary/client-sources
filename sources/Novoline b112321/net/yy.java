package net;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;
import net.minecraft.util.EnumTypeAdapterFactory;

class yy extends TypeAdapter {
   final Map a;
   final EnumTypeAdapterFactory b;

   yy(EnumTypeAdapterFactory var1, Map var2) {
      this.b = var1;
      this.a = var2;
   }

   public void write(JsonWriter var1, Object var2) throws IOException {
      var1.nullValue();
   }

   public Object read(JsonReader var1) throws IOException {
      if(var1.peek() == JsonToken.NULL) {
         var1.nextNull();
         return null;
      } else {
         return this.a.get(var1.nextString());
      }
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
