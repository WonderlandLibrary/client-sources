package net;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.Reader;

public class afy {
   public static JsonElement a(JsonParser var0, Reader var1) {
      return var0.parse(var1);
   }

   public static JsonElement a(JsonParser var0, String var1) {
      return var0.parse(var1);
   }
}
