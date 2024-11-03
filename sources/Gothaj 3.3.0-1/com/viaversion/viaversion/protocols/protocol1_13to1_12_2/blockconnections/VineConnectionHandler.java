package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

class VineConnectionHandler extends ConnectionHandler {
   private static final IntSet VINES = new IntOpenHashSet();

   static ConnectionData.ConnectorInitAction init() {
      VineConnectionHandler connectionHandler = new VineConnectionHandler();
      return blockData -> {
         if (blockData.getMinecraftKey().equals("minecraft:vine")) {
            VINES.add(blockData.getSavedBlockStateId());
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), connectionHandler);
         }
      };
   }

   @Override
   public int connect(UserConnection user, Position position, int blockState) {
      if (this.isAttachedToBlock(user, position)) {
         return blockState;
      } else {
         Position upperPos = position.getRelative(BlockFace.TOP);
         int upperBlock = this.getBlockData(user, upperPos);
         return VINES.contains(upperBlock) && this.isAttachedToBlock(user, upperPos) ? blockState : 0;
      }
   }

   private boolean isAttachedToBlock(UserConnection user, Position position) {
      return this.isAttachedToBlock(user, position, BlockFace.EAST)
         || this.isAttachedToBlock(user, position, BlockFace.WEST)
         || this.isAttachedToBlock(user, position, BlockFace.NORTH)
         || this.isAttachedToBlock(user, position, BlockFace.SOUTH);
   }

   private boolean isAttachedToBlock(UserConnection user, Position position, BlockFace blockFace) {
      return ConnectionData.OCCLUDING_STATES.contains(this.getBlockData(user, position.getRelative(blockFace)));
   }
}
