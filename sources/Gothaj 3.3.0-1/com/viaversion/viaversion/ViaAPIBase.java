package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.legacy.LegacyAPI;
import io.netty.buffer.ByteBuf;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ViaAPIBase<T> implements ViaAPI<T> {
   private final LegacyAPI<T> legacy = new LegacyAPI<>();

   @Override
   public ServerProtocolVersion getServerVersion() {
      return Via.getManager().getProtocolManager().getServerProtocolVersion();
   }

   @Override
   public int getPlayerVersion(UUID uuid) {
      UserConnection connection = Via.getManager().getConnectionManager().getConnectedClient(uuid);
      return connection != null ? connection.getProtocolInfo().getProtocolVersion() : -1;
   }

   @Override
   public String getVersion() {
      return Via.getPlatform().getPluginVersion();
   }

   @Override
   public boolean isInjected(UUID uuid) {
      return Via.getManager().getConnectionManager().isClientConnected(uuid);
   }

   @Nullable
   @Override
   public UserConnection getConnection(UUID uuid) {
      return Via.getManager().getConnectionManager().getConnectedClient(uuid);
   }

   @Override
   public void sendRawPacket(UUID uuid, ByteBuf packet) throws IllegalArgumentException {
      if (!this.isInjected(uuid)) {
         throw new IllegalArgumentException("This player is not controlled by ViaVersion!");
      } else {
         UserConnection user = Via.getManager().getConnectionManager().getConnectedClient(uuid);
         user.scheduleSendRawPacket(packet);
      }
   }

   @Override
   public SortedSet<Integer> getSupportedVersions() {
      SortedSet<Integer> outputSet = new TreeSet<>(Via.getManager().getProtocolManager().getSupportedVersions());
      BlockedProtocolVersions blockedVersions = Via.getPlatform().getConf().blockedProtocolVersions();
      outputSet.removeIf(blockedVersions::contains);
      return outputSet;
   }

   @Override
   public SortedSet<Integer> getFullSupportedVersions() {
      return Via.getManager().getProtocolManager().getSupportedVersions();
   }

   @Override
   public LegacyViaAPI<T> legacyAPI() {
      return this.legacy;
   }
}
