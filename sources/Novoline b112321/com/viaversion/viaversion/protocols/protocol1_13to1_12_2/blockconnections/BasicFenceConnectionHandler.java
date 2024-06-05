package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler;
import java.util.ArrayList;
import java.util.List;

public class BasicFenceConnectionHandler extends AbstractFenceConnectionHandler {
   static List init() {
      ArrayList var0 = new ArrayList();
      var0.add((new BasicFenceConnectionHandler("fenceConnections")).getInitAction("minecraft:oak_fence"));
      var0.add((new BasicFenceConnectionHandler("fenceConnections")).getInitAction("minecraft:birch_fence"));
      var0.add((new BasicFenceConnectionHandler("fenceConnections")).getInitAction("minecraft:jungle_fence"));
      var0.add((new BasicFenceConnectionHandler("fenceConnections")).getInitAction("minecraft:dark_oak_fence"));
      var0.add((new BasicFenceConnectionHandler("fenceConnections")).getInitAction("minecraft:acacia_fence"));
      var0.add((new BasicFenceConnectionHandler("fenceConnections")).getInitAction("minecraft:spruce_fence"));
      return var0;
   }

   public BasicFenceConnectionHandler(String var1) {
      super(var1);
   }
}
