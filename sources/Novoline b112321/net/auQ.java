package net;

import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt;

public class auQ {
   private static String[] b;

   public static Map a(Pair[] var0) {
      return MapsKt.mapOf(var0);
   }

   public static Map a(Pair var0) {
      return MapsKt.mapOf(var0);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[4]);
      }

   }
}
