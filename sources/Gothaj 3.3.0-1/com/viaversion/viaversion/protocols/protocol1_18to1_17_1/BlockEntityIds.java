package com.viaversion.viaversion.protocols.protocol1_18to1_17_1;

import com.viaversion.viaversion.api.Via;
import java.util.Arrays;

public final class BlockEntityIds {
   private static final int[] IDS = new int[14];

   public static int newId(int id) {
      int newId;
      if (id >= 0 && id <= IDS.length && (newId = IDS[id]) != -1) {
         return newId;
      } else {
         Via.getPlatform().getLogger().warning("Received out of bounds block entity id: " + id);
         return -1;
      }
   }

   public static int[] getIds() {
      return IDS;
   }

   static {
      Arrays.fill(IDS, -1);
      IDS[1] = 8;
      IDS[2] = 21;
      IDS[3] = 13;
      IDS[4] = 14;
      IDS[5] = 24;
      IDS[6] = 18;
      IDS[7] = 19;
      IDS[8] = 20;
      IDS[9] = 7;
      IDS[10] = 22;
      IDS[11] = 23;
      IDS[12] = 30;
      IDS[13] = 31;
   }
}
