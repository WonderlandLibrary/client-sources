package net;

import com.google.gson.JsonObject;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat.TagSerializer;

public class nc {
   private static boolean b;

   public static String a(JsonObject var0) {
      return TagSerializer.toString(var0);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(b()) {
         b(true);
      }

   }
}
