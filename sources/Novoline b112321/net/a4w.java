package net;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class a4w {
   private static final Int2IntMap a = new Int2IntOpenHashMap();

   public static int a(int var0) {
      return a.getOrDefault(var0, -1);
   }

   static {
      a.put(208, 113);
      a.put(209, 114);
      a.put(210, 114);
      a.put(211, 112);
      a.put(212, 152);
      a.put(213, 83);
      a.put(214, 83);
      a.put(215, 155);
      a.put(216, 143);
      a.put(217, 115);
      a.put(218, 115);
      a.put(219, 143);
      a.put(220, 127);
      a.put(221, 127);
      a.put(222, 127);
      a.put(223, 95);
      a.put(224, 127);
      a.put(225, 127);
      a.put(226, 124);
      a.put(227, 95);
      a.put(228, 187);
      a.put(229, 155);
      a.put(230, 184);
      a.put(231, 187);
      a.put(232, 127);
      a.put(233, 124);
      a.put(234, 125);
      a.put(235, 127);
   }
}
