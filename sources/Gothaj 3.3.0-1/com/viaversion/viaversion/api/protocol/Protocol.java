package com.viaversion.viaversion.api.protocol;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Protocol<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType> {
   default void registerServerbound(State state, int unmappedPacketId, int mappedPacketId) {
      this.registerServerbound(state, unmappedPacketId, mappedPacketId, (PacketHandler)null);
   }

   default void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler) {
      this.registerServerbound(state, unmappedPacketId, mappedPacketId, handler, false);
   }

   default void registerClientbound(State state, ClientboundPacketType packetType, PacketHandler handler) {
      Preconditions.checkArgument(packetType.state() == state);
      this.registerClientbound(state, packetType.getId(), packetType.getId(), handler, false);
   }

   default void registerServerbound(State state, ServerboundPacketType packetType, PacketHandler handler) {
      Preconditions.checkArgument(packetType.state() == state);
      this.registerServerbound(state, packetType.getId(), packetType.getId(), handler, false);
   }

   void registerServerbound(State var1, int var2, int var3, PacketHandler var4, boolean var5);

   void cancelServerbound(State var1, int var2);

   default void registerClientbound(State state, int unmappedPacketId, int mappedPacketId) {
      this.registerClientbound(state, unmappedPacketId, mappedPacketId, (PacketHandler)null);
   }

   default void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler) {
      this.registerClientbound(state, unmappedPacketId, mappedPacketId, handler, false);
   }

   void cancelClientbound(State var1, int var2);

   void registerClientbound(State var1, int var2, int var3, PacketHandler var4, boolean var5);

   void registerClientbound(CU var1, @Nullable PacketHandler var2);

   default void registerClientbound(CU packetType, @Nullable CM mappedPacketType) {
      this.registerClientbound(packetType, mappedPacketType, (PacketHandler)null);
   }

   default void registerClientbound(CU packetType, @Nullable CM mappedPacketType, @Nullable PacketHandler handler) {
      this.registerClientbound(packetType, mappedPacketType, handler, false);
   }

   void registerClientbound(CU var1, @Nullable CM var2, @Nullable PacketHandler var3, boolean var4);

   void cancelClientbound(CU var1);

   default void registerServerbound(SU packetType, @Nullable SM mappedPacketType) {
      this.registerServerbound(packetType, mappedPacketType, (PacketHandler)null);
   }

   void registerServerbound(SU var1, @Nullable PacketHandler var2);

   default void registerServerbound(SU packetType, @Nullable SM mappedPacketType, @Nullable PacketHandler handler) {
      this.registerServerbound(packetType, mappedPacketType, handler, false);
   }

   void registerServerbound(SU var1, @Nullable SM var2, @Nullable PacketHandler var3, boolean var4);

   void cancelServerbound(SU var1);

   default boolean hasRegisteredClientbound(CU packetType) {
      return this.hasRegisteredClientbound(packetType.state(), packetType.getId());
   }

   default boolean hasRegisteredServerbound(SU packetType) {
      return this.hasRegisteredServerbound(packetType.state(), packetType.getId());
   }

   boolean hasRegisteredClientbound(State var1, int var2);

   boolean hasRegisteredServerbound(State var1, int var2);

   void transform(Direction var1, State var2, PacketWrapper var3) throws Exception;

   @Beta
   PacketTypesProvider<CU, CM, SM, SU> getPacketTypesProvider();

   @Nullable
   <T> T get(Class<T> var1);

   void put(Object var1);

   void initialize();

   default boolean hasMappingDataToLoad() {
      return this.getMappingData() != null;
   }

   void loadMappingData();

   default void register(ViaProviders providers) {
   }

   default void init(UserConnection connection) {
   }

   @Nullable
   default MappingData getMappingData() {
      return null;
   }

   @Nullable
   default EntityRewriter<?> getEntityRewriter() {
      return null;
   }

   @Nullable
   default ItemRewriter<?> getItemRewriter() {
      return null;
   }

   default boolean isBaseProtocol() {
      return false;
   }

   @Deprecated
   default void cancelServerbound(State state, int unmappedPacketId, int mappedPacketId) {
      this.cancelServerbound(state, unmappedPacketId);
   }

   @Deprecated
   default void cancelClientbound(State state, int unmappedPacketId, int mappedPacketId) {
      this.cancelClientbound(state, unmappedPacketId);
   }

   @Deprecated
   default void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper) {
      this.registerClientbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), false);
   }

   @Deprecated
   default void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper, boolean override) {
      this.registerClientbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), override);
   }

   @Deprecated
   default void registerClientbound(CU packetType, @Nullable PacketRemapper packetRemapper) {
      this.registerClientbound(packetType, packetRemapper.asPacketHandler());
   }

   @Deprecated
   default void registerClientbound(CU packetType, @Nullable CM mappedPacketType, @Nullable PacketRemapper packetRemapper) {
      this.registerClientbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), false);
   }

   @Deprecated
   default void registerClientbound(CU packetType, @Nullable CM mappedPacketType, @Nullable PacketRemapper packetRemapper, boolean override) {
      this.registerClientbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), override);
   }

   @Deprecated
   default void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper) {
      this.registerServerbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), false);
   }

   @Deprecated
   default void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper, boolean override) {
      this.registerServerbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), override);
   }

   @Deprecated
   default void registerServerbound(SU packetType, @Nullable PacketRemapper packetRemapper) {
      this.registerServerbound(packetType, packetRemapper.asPacketHandler());
   }

   @Deprecated
   default void registerServerbound(SU packetType, @Nullable SM mappedPacketType, @Nullable PacketRemapper packetRemapper) {
      this.registerServerbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), false);
   }

   @Deprecated
   default void registerServerbound(SU packetType, @Nullable SM mappedPacketType, @Nullable PacketRemapper packetRemapper, boolean override) {
      this.registerServerbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), override);
   }
}
