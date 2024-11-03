package com.viaversion.viaversion.api.connection;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ConnectionManager {
   boolean isClientConnected(UUID var1);

   default boolean isFrontEnd(UserConnection connection) {
      return !connection.isClientSide();
   }

   @Nullable
   UserConnection getConnectedClient(UUID var1);

   @Nullable
   UUID getConnectedClientId(UserConnection var1);

   Set<UserConnection> getConnections();

   Map<UUID, UserConnection> getConnectedClients();

   void onLoginSuccess(UserConnection var1);

   void onDisconnect(UserConnection var1);
}
