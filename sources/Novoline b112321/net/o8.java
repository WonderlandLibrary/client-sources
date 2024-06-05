package net;

import com.google.common.collect.BiMap;
import net.acE;
import viaversion.viaversion.protocols.protocol1_16to1_15_2.data.MappingData;

public class o8 {
   private static acE[] b;

   public static int a(MappingData var0, int var1) {
      return var0.getNewItemId(var1);
   }

   public static int c(MappingData var0, int var1) {
      return var0.getOldItemId(var1);
   }

   public static BiMap a(MappingData var0) {
      return var0.getAttributeMappings();
   }

   public static int b(MappingData var0, int var1) {
      return var0.getNewBlockStateId(var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[5]);
      }

   }
}
