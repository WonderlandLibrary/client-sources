package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import java.util.Map;
import viaversion.viaversion.protocols.protocol1_16_2to1_16_1.data.MappingData;

public class ns {
   private static int[] b;

   public static CompoundTag b(MappingData var0) {
      return var0.getDimensionRegistry();
   }

   public static Map a(MappingData var0) {
      return var0.getDimensionDataMap();
   }

   public static int b(MappingData var0, int var1) {
      return var0.getNewItemId(var1);
   }

   public static int a(MappingData var0, int var1) {
      return var0.getOldItemId(var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[4]);
      }

   }
}
