package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import java.util.HashSet;
import java.util.Set;

public class SnowyGrassConnectionHandler extends ConnectionHandler {
   private static final Object2IntMap<SnowyGrassConnectionHandler.GrassBlock> GRASS_BLOCKS = new Object2IntOpenHashMap<>();
   private static final IntSet SNOWY_GRASS_BLOCKS = new IntOpenHashSet();

   static ConnectionData.ConnectorInitAction init() {
      Set<String> snowyGrassBlocks = new HashSet<>();
      snowyGrassBlocks.add("minecraft:grass_block");
      snowyGrassBlocks.add("minecraft:podzol");
      snowyGrassBlocks.add("minecraft:mycelium");
      GRASS_BLOCKS.defaultReturnValue(-1);
      SnowyGrassConnectionHandler handler = new SnowyGrassConnectionHandler();
      return blockData -> {
         if (snowyGrassBlocks.contains(blockData.getMinecraftKey())) {
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
            blockData.set("snowy", "true");
            GRASS_BLOCKS.put(new SnowyGrassConnectionHandler.GrassBlock(blockData.getSavedBlockStateId(), true), blockData.getBlockStateId());
            blockData.set("snowy", "false");
            GRASS_BLOCKS.put(new SnowyGrassConnectionHandler.GrassBlock(blockData.getSavedBlockStateId(), false), blockData.getBlockStateId());
         }

         if (blockData.getMinecraftKey().equals("minecraft:snow") || blockData.getMinecraftKey().equals("minecraft:snow_block")) {
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
            SNOWY_GRASS_BLOCKS.add(blockData.getSavedBlockStateId());
         }
      };
   }

   @Override
   public int connect(UserConnection user, Position position, int blockState) {
      int blockUpId = this.getBlockData(user, position.getRelative(BlockFace.TOP));
      int newId = GRASS_BLOCKS.getInt(new SnowyGrassConnectionHandler.GrassBlock(blockState, SNOWY_GRASS_BLOCKS.contains(blockUpId)));
      return newId != -1 ? newId : blockState;
   }

   private static final class GrassBlock {
      private final int blockStateId;
      private final boolean snowy;

      private GrassBlock(int blockStateId, boolean snowy) {
         this.blockStateId = blockStateId;
         this.snowy = snowy;
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            SnowyGrassConnectionHandler.GrassBlock that = (SnowyGrassConnectionHandler.GrassBlock)o;
            return this.blockStateId != that.blockStateId ? false : this.snowy == that.snowy;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int result = this.blockStateId;
         return 31 * result + (this.snowy ? 1 : 0);
      }
   }
}
