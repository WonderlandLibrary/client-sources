package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData$ConnectorInitAction;

public class NetherFenceConnectionHandler extends AbstractFenceConnectionHandler {
   static ConnectionData$ConnectorInitAction init() {
      return (new NetherFenceConnectionHandler("netherFenceConnections")).getInitAction("minecraft:nether_brick_fence");
   }

   public NetherFenceConnectionHandler(String var1) {
      super(var1);
   }
}
