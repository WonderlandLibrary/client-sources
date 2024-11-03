package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import java.util.Arrays;
import java.util.List;

public final class BlockData {
   private static final List<String> CONNECTION_TYPES = Arrays.asList("fence", "netherFence", "pane", "cobbleWall", "redstone", "allFalseIfStairPre1_12");
   private static final int MAGIC_STAIRS_ID = connectionTypeId("allFalseIfStairPre1_12");
   private final Int2ObjectMap<boolean[]> connectData = new Int2ObjectArrayMap<>();

   public void put(int blockConnectionTypeId, boolean[] booleans) {
      this.connectData.put(blockConnectionTypeId, booleans);
   }

   public boolean connectsTo(int blockConnectionTypeId, BlockFace face, boolean pre1_12AbstractFence) {
      if (pre1_12AbstractFence && this.connectData.containsKey(MAGIC_STAIRS_ID)) {
         return false;
      } else {
         boolean[] booleans = this.connectData.get(blockConnectionTypeId);
         return booleans != null && booleans[face.ordinal()];
      }
   }

   public static int connectionTypeId(String blockConnection) {
      int connectionTypeId = CONNECTION_TYPES.indexOf(blockConnection);
      Preconditions.checkArgument(connectionTypeId != -1, "Unknown connection type: " + blockConnection);
      return connectionTypeId;
   }
}
