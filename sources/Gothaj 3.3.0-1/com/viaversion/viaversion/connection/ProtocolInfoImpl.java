package com.viaversion.viaversion.connection;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.UUID;

public class ProtocolInfoImpl implements ProtocolInfo {
   private final UserConnection connection;
   private State clientState = State.HANDSHAKE;
   private State serverState = State.HANDSHAKE;
   private int protocolVersion = -1;
   private int serverProtocolVersion = -1;
   private String username;
   private UUID uuid;
   private ProtocolPipeline pipeline;

   public ProtocolInfoImpl(UserConnection connection) {
      this.connection = connection;
   }

   @Override
   public State getClientState() {
      return this.clientState;
   }

   @Override
   public void setClientState(State clientState) {
      if (Via.getManager().debugHandler().enabled()) {
         Via.getPlatform().getLogger().info("Client state changed from " + this.clientState + " to " + clientState + " for " + this.uuid);
      }

      this.clientState = clientState;
   }

   @Override
   public State getServerState() {
      return this.serverState;
   }

   @Override
   public void setServerState(State serverState) {
      if (Via.getManager().debugHandler().enabled()) {
         Via.getPlatform().getLogger().info("Server state changed from " + this.serverState + " to " + serverState + " for " + this.uuid);
      }

      this.serverState = serverState;
   }

   @Override
   public int getProtocolVersion() {
      return this.protocolVersion;
   }

   @Override
   public void setProtocolVersion(int protocolVersion) {
      ProtocolVersion protocol = ProtocolVersion.getProtocol(protocolVersion);
      this.protocolVersion = protocol.getVersion();
   }

   @Override
   public int getServerProtocolVersion() {
      return this.serverProtocolVersion;
   }

   @Override
   public void setServerProtocolVersion(int serverProtocolVersion) {
      ProtocolVersion protocol = ProtocolVersion.getProtocol(serverProtocolVersion);
      this.serverProtocolVersion = protocol.getVersion();
   }

   @Override
   public String getUsername() {
      return this.username;
   }

   @Override
   public void setUsername(String username) {
      this.username = username;
   }

   @Override
   public UUID getUuid() {
      return this.uuid;
   }

   @Override
   public void setUuid(UUID uuid) {
      this.uuid = uuid;
   }

   @Override
   public ProtocolPipeline getPipeline() {
      return this.pipeline;
   }

   @Override
   public void setPipeline(ProtocolPipeline pipeline) {
      this.pipeline = pipeline;
   }

   @Override
   public UserConnection getUser() {
      return this.connection;
   }

   @Override
   public String toString() {
      return "ProtocolInfo{clientState="
         + this.clientState
         + ", serverState="
         + this.serverState
         + ", protocolVersion="
         + this.protocolVersion
         + ", serverProtocolVersion="
         + this.serverProtocolVersion
         + ", username='"
         + this.username
         + '\''
         + ", uuid="
         + this.uuid
         + '}';
   }
}
