package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

public class RedstoneConnectionHandler extends ConnectionHandler {
   private static final IntSet REDSTONE = new IntOpenHashSet();
   private static final Int2IntMap CONNECTED_BLOCK_STATES = new Int2IntOpenHashMap(1296);
   private static final Int2IntMap POWER_MAPPINGS = new Int2IntOpenHashMap(1296);
   private static final int BLOCK_CONNECTION_TYPE_ID = BlockData.connectionTypeId("redstone");

   static ConnectionData.ConnectorInitAction init() {
      RedstoneConnectionHandler connectionHandler = new RedstoneConnectionHandler();
      String redstoneKey = "minecraft:redstone_wire";
      return blockData -> {
         if ("minecraft:redstone_wire".equals(blockData.getMinecraftKey())) {
            REDSTONE.add(blockData.getSavedBlockStateId());
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), connectionHandler);
            CONNECTED_BLOCK_STATES.put(getStates(blockData), blockData.getSavedBlockStateId());
            POWER_MAPPINGS.put(blockData.getSavedBlockStateId(), Integer.parseInt(blockData.getValue("power")));
         }
      };
   }

   private static short getStates(WrappedBlockData data) {
      short b = 0;
      b = (short)(b | getState(data.getValue("east")));
      b = (short)(b | getState(data.getValue("north")) << 2);
      b = (short)(b | getState(data.getValue("south")) << 4);
      b = (short)(b | getState(data.getValue("west")) << 6);
      return (short)(b | Integer.parseInt(data.getValue("power")) << 8);
   }

   private static int getState(String value) {
      switch (value) {
         case "none":
            return 0;
         case "side":
            return 1;
         case "up":
            return 2;
         default:
            return 0;
      }
   }

   @Override
   public int connect(UserConnection user, Position position, int blockState) {
      short b = 0;
      b = (short)(b | this.connects(user, position, BlockFace.EAST));
      b = (short)(b | this.connects(user, position, BlockFace.NORTH) << 2);
      b = (short)(b | this.connects(user, position, BlockFace.SOUTH) << 4);
      b = (short)(b | this.connects(user, position, BlockFace.WEST) << 6);
      b = (short)(b | POWER_MAPPINGS.get(blockState) << 8);
      return CONNECTED_BLOCK_STATES.getOrDefault(b, blockState);
   }

   private int connects(UserConnection user, Position position, BlockFace side) {
      Position relative = position.getRelative(side);
      int blockState = this.getBlockData(user, relative);
      if (this.connects(side, blockState)) {
         return 1;
      } else {
         int up = this.getBlockData(user, relative.getRelative(BlockFace.TOP));
         if (REDSTONE.contains(up) && !ConnectionData.OCCLUDING_STATES.contains(this.getBlockData(user, position.getRelative(BlockFace.TOP)))) {
            return 2;
         } else {
            int down = this.getBlockData(user, relative.getRelative(BlockFace.BOTTOM));
            return REDSTONE.contains(down) && !ConnectionData.OCCLUDING_STATES.contains(this.getBlockData(user, relative)) ? 1 : 0;
         }
      }
   }

   private boolean connects(BlockFace side, int blockState) {
      BlockData blockData = ConnectionData.blockConnectionData.get(blockState);
      return blockData != null && blockData.connectsTo(BLOCK_CONNECTION_TYPE_ID, side.opposite(), false);
   }
}
