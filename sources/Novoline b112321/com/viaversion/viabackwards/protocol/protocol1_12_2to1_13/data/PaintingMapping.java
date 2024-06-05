package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.aci;

public class PaintingMapping {
   private static final Int2ObjectMap a = new Int2ObjectOpenHashMap(26, 1.0F);

   public static void init() {
      a("Kebab");
      a("Aztec");
      aci.b();
      a("Alban");
      a("Aztec2");
      a("Bomb");
      a("Plant");
      a("Wasteland");
      a("Pool");
      a("Courbet");
      a("Sea");
      a("Sunset");
      a("Creebet");
      a("Wanderer");
      a("Graham");
      a("Match");
      a("Bust");
      a("Stage");
      a("Void");
      a("SkullAndRoses");
      a("Wither");
      a("Fighters");
      a("Pointer");
      a("Pigscene");
      a("BurningSkull");
      a("Skeleton");
      a("DonkeyKong");
   }

   private static void a(String var0) {
      a.put(a.size(), var0);
   }

   public static String a(int var0) {
      return (String)a.getOrDefault(var0, "kebab");
   }
}
