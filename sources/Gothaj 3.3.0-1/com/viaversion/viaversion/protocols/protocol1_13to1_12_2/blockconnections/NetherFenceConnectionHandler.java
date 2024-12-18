package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

public class NetherFenceConnectionHandler extends AbstractFenceConnectionHandler {
   static ConnectionData.ConnectorInitAction init() {
      return new NetherFenceConnectionHandler("netherFence").getInitAction("minecraft:nether_brick_fence");
   }

   public NetherFenceConnectionHandler(String blockConnections) {
      super(blockConnections);
   }
}
