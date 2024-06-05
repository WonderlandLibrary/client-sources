package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData$ConnectorInitAction;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.abi;
import net.af3;
import net.bgR;

class ChestConnectionHandler extends abi {
   private static final Map chestFacings = new HashMap();
   private static final Map connectedStates = new HashMap();
   private static final Set trappedChests = new HashSet();

   static ConnectionData$ConnectorInitAction init() {
      ChestConnectionHandler var0 = new ChestConnectionHandler();
      return ChestConnectionHandler::lambda$init$0;
   }

   private static Byte getStates(WrappedBlockData var0) {
      abi.b();
      byte var2 = 0;
      String var3 = var0.getValue("type");
      if(var3.equals("left")) {
         var2 = (byte)(var2 | 1);
      }

      if(var3.equals("right")) {
         var2 = (byte)(var2 | 2);
      }

      var2 = (byte)(var2 | BlockFace.valueOf(var0.getValue("facing").toUpperCase(Locale.ROOT)).ordinal() << 2);
      if(var0.getMinecraftKey().equals("minecraft:trapped_chest")) {
         var2 = (byte)(var2 | 16);
      }

      return Byte.valueOf(var2);
   }

   public int a(bgR var1, Position var2, int var3) {
      abi.b();
      BlockFace var5 = (BlockFace)chestFacings.get(Integer.valueOf(var3));
      byte var6 = 0;
      var6 = (byte)(var6 | var5.ordinal() << 2);
      boolean var7 = trappedChests.contains(Integer.valueOf(var3));
      if(var7) {
         var6 = (byte)(var6 | 16);
      }

      int var8;
      if(chestFacings.containsKey(Integer.valueOf(var8 = this.a(var1, var2.getRelative(BlockFace.NORTH)))) && var7 == trappedChests.contains(Integer.valueOf(var8))) {
         var6 = (byte)(var6 | (var5 == BlockFace.WEST?1:2));
      }

      if(chestFacings.containsKey(Integer.valueOf(var8 = this.a(var1, var2.getRelative(BlockFace.SOUTH)))) && var7 == trappedChests.contains(Integer.valueOf(var8))) {
         var6 = (byte)(var6 | (var5 == BlockFace.EAST?1:2));
      }

      if(chestFacings.containsKey(Integer.valueOf(var8 = this.a(var1, var2.getRelative(BlockFace.WEST)))) && var7 == trappedChests.contains(Integer.valueOf(var8))) {
         var6 = (byte)(var6 | (var5 == BlockFace.NORTH?2:1));
      }

      if(chestFacings.containsKey(Integer.valueOf(var8 = this.a(var1, var2.getRelative(BlockFace.EAST)))) && var7 == trappedChests.contains(Integer.valueOf(var8))) {
         var6 = (byte)(var6 | (var5 == BlockFace.SOUTH?2:1));
      }

      Integer var9 = (Integer)connectedStates.get(Byte.valueOf(var6));
      return var9 == null?var3:var9.intValue();
   }

   private static void lambda$init$0(ChestConnectionHandler var0, WrappedBlockData var1) {
      PacketRemapper[] var2 = abi.b();
      if(var1.getMinecraftKey().equals("minecraft:chest") || var1.getMinecraftKey().equals("minecraft:trapped_chest")) {
         if(!var1.getValue("waterlogged").equals("true")) {
            chestFacings.put(Integer.valueOf(var1.getSavedBlockStateId()), BlockFace.valueOf(var1.getValue("facing").toUpperCase(Locale.ROOT)));
            if(var1.getMinecraftKey().equalsIgnoreCase("minecraft:trapped_chest")) {
               trappedChests.add(Integer.valueOf(var1.getSavedBlockStateId()));
            }

            connectedStates.put(getStates(var1), Integer.valueOf(var1.getSavedBlockStateId()));
            af3.h.put(var1.getSavedBlockStateId(), var0);
         }
      }
   }
}
