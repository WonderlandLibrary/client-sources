package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractStempConnectionHandler extends ConnectionHandler {
   private static final BlockFace[] BLOCK_FACES = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
   private final IntSet blockId = new IntOpenHashSet();
   private final int baseStateId;
   private final Map<BlockFace, Integer> stemps = new EnumMap<>(BlockFace.class);

   protected AbstractStempConnectionHandler(String baseStateId) {
      this.baseStateId = ConnectionData.getId(baseStateId);
   }

   public ConnectionData.ConnectorInitAction getInitAction(String blockId, String toKey) {
      return blockData -> {
         if (blockData.getSavedBlockStateId() == this.baseStateId || blockId.equals(blockData.getMinecraftKey())) {
            if (blockData.getSavedBlockStateId() != this.baseStateId) {
               this.blockId.add(blockData.getSavedBlockStateId());
            }

            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), this);
         }

         if (blockData.getMinecraftKey().equals(toKey)) {
            String facing = blockData.getValue("facing").toUpperCase(Locale.ROOT);
            this.stemps.put(BlockFace.valueOf(facing), blockData.getSavedBlockStateId());
         }
      };
   }

   @Override
   public int connect(UserConnection user, Position position, int blockState) {
      if (blockState != this.baseStateId) {
         return blockState;
      } else {
         for (BlockFace blockFace : BLOCK_FACES) {
            if (this.blockId.contains(this.getBlockData(user, position.getRelative(blockFace)))) {
               return this.stemps.get(blockFace);
            }
         }

         return this.baseStateId;
      }
   }
}
