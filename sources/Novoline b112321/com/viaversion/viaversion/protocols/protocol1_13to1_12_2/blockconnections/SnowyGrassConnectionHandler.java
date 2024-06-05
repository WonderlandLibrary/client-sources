package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData$ConnectorInitAction;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.abi;
import net.af3;
import net.bgR;

public class SnowyGrassConnectionHandler extends abi {
   private static final Map grassBlocks = new HashMap();
   private static final Set snows = new HashSet();

   static ConnectionData$ConnectorInitAction init() {
      HashSet var0 = new HashSet();
      var0.add("minecraft:grass_block");
      var0.add("minecraft:podzol");
      var0.add("minecraft:mycelium");
      SnowyGrassConnectionHandler var1 = new SnowyGrassConnectionHandler();
      return SnowyGrassConnectionHandler::lambda$init$0;
   }

   public int a(bgR var1, Position var2, int var3) {
      int var5 = this.a(var1, var2.getRelative(BlockFace.TOP));
      abi.b();
      Integer var6 = (Integer)grassBlocks.get(new Pair(Integer.valueOf(var3), Boolean.valueOf(snows.contains(Integer.valueOf(var5)))));
      return var6 != null?var6.intValue():var3;
   }

   private static void lambda$init$0(Set var0, SnowyGrassConnectionHandler var1, WrappedBlockData var2) {
      PacketRemapper[] var3 = abi.b();
      if(var0.contains(var2.getMinecraftKey())) {
         af3.h.put(var2.getSavedBlockStateId(), var1);
         var2.set("snowy", "true");
         grassBlocks.put(new Pair(Integer.valueOf(var2.getSavedBlockStateId()), Boolean.valueOf(true)), Integer.valueOf(var2.getBlockStateId()));
         var2.set("snowy", "false");
         grassBlocks.put(new Pair(Integer.valueOf(var2.getSavedBlockStateId()), Boolean.valueOf(false)), Integer.valueOf(var2.getBlockStateId()));
      }

      if(var2.getMinecraftKey().equals("minecraft:snow") || var2.getMinecraftKey().equals("minecraft:snow_block")) {
         af3.h.put(var2.getSavedBlockStateId(), var1);
         snows.add(Integer.valueOf(var2.getSavedBlockStateId()));
      }

   }
}
