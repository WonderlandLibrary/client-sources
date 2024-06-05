package net;

import com.google.gson.JsonPrimitive;

public class mH {
   public static String d(JsonPrimitive var0) {
      return var0.getAsString();
   }

   public static boolean g(JsonPrimitive var0) {
      return var0.isString();
   }

   public static boolean f(JsonPrimitive var0) {
      return var0.isBoolean();
   }

   public static boolean c(JsonPrimitive var0) {
      return var0.isNumber();
   }

   public static int b(JsonPrimitive var0) {
      return var0.getAsInt();
   }

   public static short e(JsonPrimitive var0) {
      return var0.getAsShort();
   }

   public static boolean a(JsonPrimitive var0) {
      return var0.getAsBoolean();
   }
}
