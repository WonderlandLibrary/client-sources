package net;

import java.util.Map;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import viaversion.viaversion.api.minecraft.Position;

public class hH {
   private static String[] b;

   public static void b(BackwardsBlockStorage var0) {
      var0.clear();
   }

   public static void a(BackwardsBlockStorage var0, Position var1, int var2) {
      var0.checkAndStore(var1, var2);
   }

   public static Integer a(BackwardsBlockStorage var0, Position var1) {
      return var0.get(var1);
   }

   public static Map a(BackwardsBlockStorage var0) {
      return var0.getBlocks();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[1]);
      }

   }
}
