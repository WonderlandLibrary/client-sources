package net;

import java.util.regex.Matcher;

public class bgI {
   private static boolean b;

   public static boolean c(Matcher var0) {
      return var0.matches();
   }

   public static String a(Matcher var0, String var1) {
      return var0.group(var1);
   }

   public static String b(Matcher var0, String var1) {
      return var0.replaceAll(var1);
   }

   public static boolean b(Matcher var0, int var1) {
      return var0.find(var1);
   }

   public static int b(Matcher var0) {
      return var0.start();
   }

   public static int d(Matcher var0) {
      return var0.end();
   }

   public static String a(Matcher var0, int var1) {
      return var0.group(var1);
   }

   public static boolean a(Matcher var0) {
      return var0.find();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(b()) {
         b(true);
      }

   }
}
