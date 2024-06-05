package net;

public class j5 {
   private static String[] b;

   public static byte a(Byte var0) {
      return var0.byteValue();
   }

   public static Byte b(byte var0) {
      return Byte.valueOf(var0);
   }

   public static byte a(String var0) {
      return Byte.parseByte(var0);
   }

   public static int b(Byte var0) {
      return var0.intValue();
   }

   public static Byte b(String var0) {
      return Byte.valueOf(var0);
   }

   public static String a(byte var0) {
      return Byte.toString(var0);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[5]);
      }

   }
}
