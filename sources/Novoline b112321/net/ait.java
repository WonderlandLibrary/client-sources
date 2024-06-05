package net;

import java.lang.reflect.Modifier;

public class ait {
   public static boolean c(int var0) {
      return Modifier.isFinal(var0);
   }

   public static boolean a(int var0) {
      return Modifier.isAbstract(var0);
   }

   public static boolean d(int var0) {
      return Modifier.isStatic(var0);
   }

   public static String b(int var0) {
      return Modifier.toString(var0);
   }
}
