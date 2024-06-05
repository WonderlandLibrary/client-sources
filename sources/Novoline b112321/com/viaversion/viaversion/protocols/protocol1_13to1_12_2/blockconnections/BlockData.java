package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import java.util.HashMap;
import java.util.Map;
import net.abi;

public class BlockData {
   private final Map connectData = new HashMap();

   public void put(String var1, boolean[] var2) {
      this.connectData.put(var1, var2);
   }

   public boolean connectsTo(String var1, BlockFace var2, boolean var3) {
      abi.b();
      Object var5 = null;
      boolean[] var6 = (boolean[])this.connectData.get("allFalseIfStairPre1_12");
      if(var6 == null) {
         var6 = (boolean[])this.connectData.get(var1);
      }

      return var6 != null && var6[var2.ordinal()];
   }
}
