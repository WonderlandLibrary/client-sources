package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.Arrays;
import java.util.Locale;

class ChestConnectionHandler extends ConnectionHandler {
   private static final Int2ObjectMap<BlockFace> CHEST_FACINGS = new Int2ObjectOpenHashMap<>();
   private static final int[] CONNECTED_STATES = new int[32];
   private static final IntSet TRAPPED_CHESTS = new IntOpenHashSet();

   static ConnectionData.ConnectorInitAction init() {
      Arrays.fill(CONNECTED_STATES, -1);
      ChestConnectionHandler connectionHandler = new ChestConnectionHandler();
      return blockData -> {
         if (blockData.getMinecraftKey().equals("minecraft:chest") || blockData.getMinecraftKey().equals("minecraft:trapped_chest")) {
            if (!blockData.getValue("waterlogged").equals("true")) {
               CHEST_FACINGS.put(blockData.getSavedBlockStateId(), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
               if (blockData.getMinecraftKey().equalsIgnoreCase("minecraft:trapped_chest")) {
                  TRAPPED_CHESTS.add(blockData.getSavedBlockStateId());
               }

               CONNECTED_STATES[getStates(blockData)] = blockData.getSavedBlockStateId();
               ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), connectionHandler);
            }
         }
      };
   }

   private static Byte getStates(WrappedBlockData blockData) {
      byte states = 0;
      String type = blockData.getValue("type");
      if (type.equals("left")) {
         states = (byte)(states | 1);
      }

      if (type.equals("right")) {
         states = (byte)(states | 2);
      }

      states = (byte)(states | BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)).ordinal() << 2);
      if (blockData.getMinecraftKey().equals("minecraft:trapped_chest")) {
         states = (byte)(states | 16);
      }

      return states;
   }

   @Override
   public int connect(UserConnection user, Position position, int blockState) {
      BlockFace facing = CHEST_FACINGS.get(blockState);
      byte states = 0;
      states = (byte)(states | facing.ordinal() << 2);
      boolean trapped = TRAPPED_CHESTS.contains(blockState);
      if (trapped) {
         states = (byte)(states | 16);
      }

      int relative;
      if (CHEST_FACINGS.containsKey(relative = this.getBlockData(user, position.getRelative(BlockFace.NORTH))) && trapped == TRAPPED_CHESTS.contains(relative)) {
         states = (byte)(states | (facing == BlockFace.WEST ? 1 : 2));
      } else if (CHEST_FACINGS.containsKey(relative = this.getBlockData(user, position.getRelative(BlockFace.SOUTH)))
         && trapped == TRAPPED_CHESTS.contains(relative)) {
         states = (byte)(states | (facing == BlockFace.EAST ? 1 : 2));
      } else if (CHEST_FACINGS.containsKey(relative = this.getBlockData(user, position.getRelative(BlockFace.WEST)))
         && trapped == TRAPPED_CHESTS.contains(relative)) {
         states = (byte)(states | (facing == BlockFace.NORTH ? 2 : 1));
      } else if (CHEST_FACINGS.containsKey(relative = this.getBlockData(user, position.getRelative(BlockFace.EAST)))
         && trapped == TRAPPED_CHESTS.contains(relative)) {
         states = (byte)(states | (facing == BlockFace.SOUTH ? 2 : 1));
      }

      int newBlockState = CONNECTED_STATES[states];
      return newBlockState == -1 ? blockState : newBlockState;
   }
}
