package com.viaversion.viaversion.api.connection;

import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ProtocolInfo {
   @Deprecated
   default State getState() {
      return this.getServerState();
   }

   State getClientState();

   State getServerState();

   default State getState(Direction direction) {
      return direction == Direction.CLIENTBOUND ? this.getServerState() : this.getClientState();
   }

   default void setState(State state) {
      this.setClientState(state);
      this.setServerState(state);
   }

   void setClientState(State var1);

   void setServerState(State var1);

   int getProtocolVersion();

   void setProtocolVersion(int var1);

   int getServerProtocolVersion();

   void setServerProtocolVersion(int var1);

   @Nullable
   String getUsername();

   void setUsername(String var1);

   @Nullable
   UUID getUuid();

   void setUuid(UUID var1);

   ProtocolPipeline getPipeline();

   void setPipeline(ProtocolPipeline var1);

   @Deprecated
   UserConnection getUser();
}
