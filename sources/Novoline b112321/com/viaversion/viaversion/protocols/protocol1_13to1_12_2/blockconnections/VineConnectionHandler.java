package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData$ConnectorInitAction;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.HashSet;
import java.util.Set;
import net.abi;
import net.af3;
import net.bgR;

class VineConnectionHandler extends abi {
   private static final Set vines = new HashSet();

   static ConnectionData$ConnectorInitAction init() {
      VineConnectionHandler var0 = new VineConnectionHandler();
      return VineConnectionHandler::lambda$init$0;
   }

   public int a(bgR var1, Position var2, int var3) {
      PacketRemapper[] var4 = abi.b();
      if(this.a(var1, var2)) {
         return var3;
      } else {
         Position var5 = var2.getRelative(BlockFace.TOP);
         int var6 = this.a(var1, var5);
         return vines.contains(Integer.valueOf(var6)) && this.a(var1, var5)?var3:0;
      }
   }

   private boolean a(bgR var1, Position var2) {
      PacketRemapper[] var3 = abi.b();
      return this.a(var1, var2, BlockFace.EAST) || this.a(var1, var2, BlockFace.WEST) || this.a(var1, var2, BlockFace.NORTH) || this.a(var1, var2, BlockFace.SOUTH);
   }

   private boolean a(bgR var1, Position var2, BlockFace var3) {
      return af3.b.contains(this.a(var1, var2.getRelative(var3)));
   }

   private static void lambda$init$0(VineConnectionHandler var0, WrappedBlockData var1) {
      PacketRemapper[] var2 = abi.b();
      if(var1.getMinecraftKey().equals("minecraft:vine")) {
         vines.add(Integer.valueOf(var1.getSavedBlockStateId()));
         af3.h.put(var1.getSavedBlockStateId(), var0);
      }
   }
}
