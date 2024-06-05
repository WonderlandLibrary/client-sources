package net;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import viaversion.viaversion.api.Pair;

public class a4D {
   private static final Int2ObjectMap a = new Int2ObjectOpenHashMap(37, 1.0F);
   private static boolean b;

   public static Pair a(int var0) {
      return (Pair)a.get(var0);
   }

   private static void a(int var0, int var1, boolean var2) {
      a.put(var0, new Pair(Integer.valueOf(var1), Boolean.valueOf(var2)));
   }

   static {
      a(0, 3694022, false);
      a(1, 3694022, false);
      a(2, 3694022, false);
      a(3, 3694022, false);
      a(4, 3694022, false);
      a(5, 2039713, false);
      b(true);
      a(6, 2039713, false);
      a(7, 8356754, false);
      a(8, 8356754, false);
      a(9, 2293580, false);
      a(10, 2293580, false);
      a(11, 2293580, false);
      a(12, 14981690, false);
      a(13, 14981690, false);
      a(14, 8171462, false);
      a(15, 8171462, false);
      a(16, 8171462, false);
      a(17, 5926017, false);
      a(18, 5926017, false);
      a(19, 3035801, false);
      a(20, 3035801, false);
      a(21, 16262179, true);
      a(22, 16262179, true);
      a(23, 4393481, true);
      a(24, 4393481, true);
      a(25, 5149489, false);
      a(26, 5149489, false);
      a(27, 5149489, false);
      a(28, 13458603, false);
      a(29, 13458603, false);
      a(30, 13458603, false);
      a(31, 9643043, false);
      a(32, 9643043, false);
      a(33, 9643043, false);
      a(34, 4738376, false);
      a(35, 4738376, false);
      a(36, 3381504, false);
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
}
