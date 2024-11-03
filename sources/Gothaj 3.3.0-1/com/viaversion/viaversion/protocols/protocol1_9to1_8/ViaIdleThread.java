package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;

public class ViaIdleThread implements Runnable {
   @Override
   public void run() {
      for (UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
         ProtocolInfo protocolInfo = info.getProtocolInfo();
         if (protocolInfo != null && protocolInfo.getPipeline().contains(Protocol1_9To1_8.class)) {
            MovementTracker movementTracker = info.get(MovementTracker.class);
            if (movementTracker != null) {
               long nextIdleUpdate = movementTracker.getNextIdlePacket();
               if (nextIdleUpdate <= System.currentTimeMillis() && info.getChannel().isOpen()) {
                  Via.getManager().getProviders().get(MovementTransmitterProvider.class).sendPlayer(info);
               }
            }
         }
      }
   }
}
