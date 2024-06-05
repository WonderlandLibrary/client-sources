package net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import viaversion.viaversion.util.GsonUtil;

public class ua {
   private static boolean b;

   public static JsonParser d() {
      return GsonUtil.getJsonParser();
   }

   public static Gson a() {
      return GsonUtil.getGson();
   }

   public static GsonBuilder c() {
      return GsonUtil.getGsonBuilder();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean e() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
