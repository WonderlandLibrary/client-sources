package net;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class aat {
   private static final Int2IntMap a = new Int2IntOpenHashMap(19, 1.0F);

   public static int a(int var0) {
      return a.getOrDefault(var0, var0);
   }

   public static boolean b(int var0) {
      return a.containsKey(var0);
   }

   private static void a(int var0, int var1) {
      a.put(var0, var1);
   }

   static {
      a(1005, 1010);
      a(1003, 1005);
      a(1006, 1011);
      a(1004, 1009);
      a(1007, 1015);
      a(1008, 1016);
      a(1009, 1016);
      a(1010, 1019);
      a(1011, 1020);
      a(1012, 1021);
      a(1014, 1024);
      a(1015, 1025);
      a(1016, 1026);
      a(1017, 1027);
      a(1020, 1029);
      a(1021, 1030);
      a(1022, 1031);
      a(1013, 1023);
      a(1018, 1028);
   }
}
