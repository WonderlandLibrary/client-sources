package com.viaversion.viaversion.api.protocol;

import com.google.common.collect.Range;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.CompletableFuture;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ProtocolManager {
   ServerProtocolVersion getServerProtocolVersion();

   @Nullable
   <T extends Protocol> T getProtocol(Class<T> var1);

   @Nullable
   default Protocol getProtocol(ProtocolVersion clientVersion, ProtocolVersion serverVersion) {
      return this.getProtocol(clientVersion.getVersion(), serverVersion.getVersion());
   }

   @Nullable
   Protocol getProtocol(int var1, int var2);

   Protocol getBaseProtocol();

   Protocol getBaseProtocol(int var1);

   Collection<Protocol<?, ?, ?, ?>> getProtocols();

   @Deprecated
   default boolean isBaseProtocol(Protocol protocol) {
      return protocol.isBaseProtocol();
   }

   void registerProtocol(Protocol var1, ProtocolVersion var2, ProtocolVersion var3);

   void registerProtocol(Protocol var1, List<Integer> var2, int var3);

   void registerBaseProtocol(Protocol var1, Range<Integer> var2);

   @Nullable
   List<ProtocolPathEntry> getProtocolPath(int var1, int var2);

   <C extends ClientboundPacketType, S extends ServerboundPacketType> VersionedPacketTransformer<C, S> createPacketTransformer(
      ProtocolVersion var1, @Nullable Class<C> var2, @Nullable Class<S> var3
   );

   void setMaxPathDeltaIncrease(int var1);

   int getMaxPathDeltaIncrease();

   @Deprecated
   default void setOnlyCheckLoweringPathEntries(boolean onlyCheckLoweringPathEntries) {
      this.setMaxPathDeltaIncrease(onlyCheckLoweringPathEntries ? 0 : -1);
   }

   @Deprecated
   default boolean onlyCheckLoweringPathEntries() {
      return this.getMaxPathDeltaIncrease() != -1;
   }

   int getMaxProtocolPathSize();

   void setMaxProtocolPathSize(int var1);

   SortedSet<Integer> getSupportedVersions();

   boolean isWorkingPipe();

   void completeMappingDataLoading(Class<? extends Protocol> var1) throws Exception;

   boolean checkForMappingCompletion();

   void addMappingLoaderFuture(Class<? extends Protocol> var1, Runnable var2);

   void addMappingLoaderFuture(Class<? extends Protocol> var1, Class<? extends Protocol> var2, Runnable var3);

   @Nullable
   CompletableFuture<Void> getMappingLoaderFuture(Class<? extends Protocol> var1);

   PacketWrapper createPacketWrapper(@Nullable PacketType var1, @Nullable ByteBuf var2, UserConnection var3);

   @Deprecated
   PacketWrapper createPacketWrapper(int var1, @Nullable ByteBuf var2, UserConnection var3);
}
