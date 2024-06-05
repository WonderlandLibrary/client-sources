package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.BlockData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData$ConnectorInitAction;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.HashSet;
import java.util.Set;
import net.abi;
import net.af3;
import net.bgR;

public class RedstoneConnectionHandler extends abi {
   private static final Set redstone = new HashSet();
   private static final Int2IntMap d = new Int2IntOpenHashMap(1296);
   private static final Int2IntMap c = new Int2IntOpenHashMap(1296);

   static ConnectionData$ConnectorInitAction init() {
      RedstoneConnectionHandler var0 = new RedstoneConnectionHandler();
      String var1 = "minecraft:redstone_wire";
      return RedstoneConnectionHandler::lambda$init$0;
   }

   private static short getStates(WrappedBlockData var0) {
      short var1 = 0;
      var1 = (short)(var1 | getState(var0.getValue("east")));
      var1 = (short)(var1 | getState(var0.getValue("north")) << 2);
      var1 = (short)(var1 | getState(var0.getValue("south")) << 4);
      var1 = (short)(var1 | getState(var0.getValue("west")) << 6);
      var1 = (short)(var1 | Integer.parseInt(var0.getValue("power")) << 8);
      return var1;
   }

   private static int getState(String var0) {
      abi.b();
      byte var3 = -1;
      switch(var0.hashCode()) {
      case 3387192:
         if(!var0.equals("none")) {
            break;
         }

         var3 = 0;
      case 3530071:
         if(!var0.equals("side")) {
            break;
         }

         var3 = 1;
      case 3739:
         if(var0.equals("up")) {
            var3 = 2;
         }
      }

      switch(var3) {
      case 0:
         return 0;
      case 1:
         return 1;
      case 2:
         return 2;
      default:
         return 0;
      }
   }

   public int a(bgR var1, Position var2, int var3) {
      short var4 = 0;
      var4 = (short)(var4 | this.a(var1, var2, BlockFace.EAST));
      var4 = (short)(var4 | this.a(var1, var2, BlockFace.NORTH) << 2);
      var4 = (short)(var4 | this.a(var1, var2, BlockFace.SOUTH) << 4);
      var4 = (short)(var4 | this.a(var1, var2, BlockFace.WEST) << 6);
      var4 = (short)(var4 | c.get(var3) << 8);
      return d.getOrDefault(var4, var3);
   }

   private int a(bgR var1, Position var2, BlockFace var3) {
      abi.b();
      Position var5 = var2.getRelative(var3);
      int var6 = this.a(var1, var5);
      if(this.connects(var3, var6)) {
         return 1;
      } else {
         int var7 = this.a(var1, var5.getRelative(BlockFace.TOP));
         if(redstone.contains(Integer.valueOf(var7)) && !af3.b.contains(this.a(var1, var2.getRelative(BlockFace.TOP)))) {
            return 2;
         } else {
            int var8 = this.a(var1, var5.getRelative(BlockFace.BOTTOM));
            return redstone.contains(Integer.valueOf(var8)) && !af3.b.contains(this.a(var1, var5))?1:0;
         }
      }
   }

   private boolean connects(BlockFace var1, int var2) {
      abi.b();
      BlockData var4 = (BlockData)af3.c.get(var2);
      return var4 != null && var4.connectsTo("redstoneConnections", var1.opposite(), false);
   }

   private static void lambda$init$0(RedstoneConnectionHandler var0, WrappedBlockData var1) {
      PacketRemapper[] var2 = abi.b();
      if("minecraft:redstone_wire".equals(var1.getMinecraftKey())) {
         redstone.add(Integer.valueOf(var1.getSavedBlockStateId()));
         af3.h.put(var1.getSavedBlockStateId(), var0);
         d.put(getStates(var1), var1.getSavedBlockStateId());
         c.put(var1.getSavedBlockStateId(), Integer.parseInt(var1.getValue("power")));
      }
   }
}
