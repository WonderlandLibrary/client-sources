package net;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Map;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.BackwardsMappings;
import viaversion.viaversion.api.data.Mappings;
import viaversion.viaversion.util.Int2IntBiMap;

public class aqj {
   private static int b;

   public static Mappings d(BackwardsMappings var0) {
      return var0.getSoundMappings();
   }

   public static Int2IntBiMap e(BackwardsMappings var0) {
      return var0.getItemMappings();
   }

   public static int a(BackwardsMappings var0, int var1) {
      return var0.getNewBlockStateId(var1);
   }

   public static Map f(BackwardsMappings var0) {
      return var0.getTranslateMappings();
   }

   public static Mappings b(BackwardsMappings var0) {
      return var0.getEnchantmentMappings();
   }

   public static String a(BackwardsMappings var0, String var1) {
      return var0.getMappedNamedSound(var1);
   }

   public static Int2ObjectMap a(BackwardsMappings var0) {
      return var0.getStatisticMappings();
   }

   public static void c(BackwardsMappings var0) {
      var0.load();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 90;
   }

   static {
      if(c() != 0) {
         b(24);
      }

   }
}
