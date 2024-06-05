package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData$ConnectorInitAction;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.HashSet;
import java.util.Set;
import net.abi;
import net.af3;
import net.bgR;

public class FlowerConnectionHandler extends abi {
   private static final Int2IntMap a = new Int2IntOpenHashMap();

   static ConnectionData$ConnectorInitAction init() {
      HashSet var0 = new HashSet();
      var0.add("minecraft:rose_bush");
      var0.add("minecraft:sunflower");
      var0.add("minecraft:peony");
      var0.add("minecraft:tall_grass");
      var0.add("minecraft:large_fern");
      var0.add("minecraft:lilac");
      FlowerConnectionHandler var1 = new FlowerConnectionHandler();
      return FlowerConnectionHandler::lambda$init$0;
   }

   public int a(bgR var1, Position var2, int var3) {
      abi.b();
      int var5 = this.a(var1, var2.getRelative(BlockFace.BOTTOM));
      int var6 = a.get(var5);
      if(var6 != 0) {
         int var7 = this.a(var1, var2.getRelative(BlockFace.TOP));
         if(Via.c().isStemWhenBlockAbove()) {
            if(var7 == 0) {
               return var6;
            }
         } else if(!a.containsKey(var7)) {
            return var6;
         }
      }

      return var3;
   }

   private static void lambda$init$0(Set var0, FlowerConnectionHandler var1, WrappedBlockData var2) {
      PacketRemapper[] var3 = abi.b();
      if(var0.contains(var2.getMinecraftKey())) {
         af3.h.put(var2.getSavedBlockStateId(), var1);
         if(var2.getValue("half").equals("lower")) {
            var2.set("half", "upper");
            a.put(var2.getSavedBlockStateId(), var2.getBlockStateId());
         }
      }

   }
}
