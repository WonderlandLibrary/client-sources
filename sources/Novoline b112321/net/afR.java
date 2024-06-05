package net;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class afR {
   private static int b;

   public static boolean g(JsonElement var0) {
      return var0.isJsonObject();
   }

   public static JsonObject a(JsonElement var0) {
      return var0.getAsJsonObject();
   }

   public static String h(JsonElement var0) {
      return var0.getAsString();
   }

   public static boolean k(JsonElement var0) {
      return var0.isJsonArray();
   }

   public static JsonArray d(JsonElement var0) {
      return var0.getAsJsonArray();
   }

   public static boolean b(JsonElement var0) {
      return var0.isJsonPrimitive();
   }

   public static JsonPrimitive m(JsonElement var0) {
      return var0.getAsJsonPrimitive();
   }

   public static boolean f(JsonElement var0) {
      return var0.getAsBoolean();
   }

   public static int j(JsonElement var0) {
      return var0.getAsInt();
   }

   public static long l(JsonElement var0) {
      return var0.getAsLong();
   }

   public static float c(JsonElement var0) {
      return var0.getAsFloat();
   }

   public static boolean e(JsonElement var0) {
      return var0.isJsonNull();
   }

   public static byte i(JsonElement var0) {
      return var0.getAsByte();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 36;
   }

   static {
      if(a() != 0) {
         b(107);
      }

   }
}
