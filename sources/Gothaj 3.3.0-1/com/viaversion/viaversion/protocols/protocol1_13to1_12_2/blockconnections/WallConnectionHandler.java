package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import java.util.ArrayList;
import java.util.List;

public class WallConnectionHandler extends AbstractFenceConnectionHandler {
   private static final BlockFace[] BLOCK_FACES = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
   private static final int[] OPPOSITES = new int[]{3, 2, 1, 0};

   static List<ConnectionData.ConnectorInitAction> init() {
      List<ConnectionData.ConnectorInitAction> actions = new ArrayList<>(2);
      actions.add(new WallConnectionHandler("cobbleWall").getInitAction("minecraft:cobblestone_wall"));
      actions.add(new WallConnectionHandler("cobbleWall").getInitAction("minecraft:mossy_cobblestone_wall"));
      return actions;
   }

   public WallConnectionHandler(String blockConnections) {
      super(blockConnections);
   }

   @Override
   protected byte getStates(WrappedBlockData blockData) {
      byte states = super.getStates(blockData);
      if (blockData.getValue("up").equals("true")) {
         states = (byte)(states | 16);
      }

      return states;
   }

   @Override
   protected byte getStates(UserConnection user, Position position, int blockState) {
      byte states = super.getStates(user, position, blockState);
      if (this.up(user, position)) {
         states = (byte)(states | 16);
      }

      return states;
   }

   @Override
   protected byte statesSize() {
      return 32;
   }

   public boolean up(UserConnection user, Position position) {
      if (!this.isWall(this.getBlockData(user, position.getRelative(BlockFace.BOTTOM)))
         && !this.isWall(this.getBlockData(user, position.getRelative(BlockFace.TOP)))) {
         int blockFaces = this.getBlockFaces(user, position);
         if (blockFaces != 0 && blockFaces != 15) {
            for (int i = 0; i < BLOCK_FACES.length; i++) {
               if ((blockFaces & 1 << i) != 0 && (blockFaces & 1 << OPPOSITES[i]) == 0) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   private int getBlockFaces(UserConnection user, Position position) {
      int blockFaces = 0;

      for (int i = 0; i < BLOCK_FACES.length; i++) {
         if (this.isWall(this.getBlockData(user, position.getRelative(BLOCK_FACES[i])))) {
            blockFaces |= 1 << i;
         }
      }

      return blockFaces;
   }

   private boolean isWall(int id) {
      return this.getBlockStates().contains(id);
   }
}
