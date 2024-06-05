package net;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.Reader;
import java.lang.reflect.Type;

public class akr {
   private static String[] b;

   public static Object a(Gson var0, Reader var1, Type var2) {
      return var0.fromJson(var1, var2);
   }

   public static String b(Gson var0, Object var1) {
      return var0.toJson(var1);
   }

   public static Object a(Gson var0, JsonElement var1, Class var2) {
      return var0.fromJson(var1, var2);
   }

   public static String a(Gson var0, JsonElement var1) {
      return var0.toJson(var1);
   }

   public static Object a(Gson var0, String var1, Class var2) {
      return var0.fromJson(var1, var2);
   }

   public static Object a(Gson var0, String var1, Type var2) {
      return var0.fromJson(var1, var2);
   }

   public static Object a(Gson var0, Reader var1, Class var2) {
      return var0.fromJson(var1, var2);
   }

   public static JsonElement a(Gson var0, Object var1) {
      return var0.toJsonTree(var1);
   }

   public static Object a(Gson var0, JsonElement var1, Type var2) {
      return var0.fromJson(var1, var2);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[1]);
      }

   }
}
