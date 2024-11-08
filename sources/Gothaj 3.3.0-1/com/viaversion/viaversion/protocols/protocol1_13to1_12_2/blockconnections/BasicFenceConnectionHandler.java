package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.ArrayList;
import java.util.List;

public class BasicFenceConnectionHandler extends AbstractFenceConnectionHandler {
   static List<ConnectionData.ConnectorInitAction> init() {
      List<ConnectionData.ConnectorInitAction> actions = new ArrayList<>();
      actions.add(new BasicFenceConnectionHandler("fence").getInitAction("minecraft:oak_fence"));
      actions.add(new BasicFenceConnectionHandler("fence").getInitAction("minecraft:birch_fence"));
      actions.add(new BasicFenceConnectionHandler("fence").getInitAction("minecraft:jungle_fence"));
      actions.add(new BasicFenceConnectionHandler("fence").getInitAction("minecraft:dark_oak_fence"));
      actions.add(new BasicFenceConnectionHandler("fence").getInitAction("minecraft:acacia_fence"));
      actions.add(new BasicFenceConnectionHandler("fence").getInitAction("minecraft:spruce_fence"));
      return actions;
   }

   public BasicFenceConnectionHandler(String blockConnections) {
      super(blockConnections);
   }
}
