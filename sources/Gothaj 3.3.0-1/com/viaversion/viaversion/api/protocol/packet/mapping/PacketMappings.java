package com.viaversion.viaversion.api.protocol.packet.mapping;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketMappings {
   @Nullable
   PacketMapping mappedPacket(State var1, int var2);

   default boolean hasMapping(PacketType packetType) {
      return this.mappedPacket(packetType.state(), packetType.getId()) != null;
   }

   default boolean hasMapping(State state, int unmappedId) {
      return this.mappedPacket(state, unmappedId) != null;
   }

   default void addMapping(PacketType packetType, PacketMapping mapping) {
      this.addMapping(packetType.state(), packetType.getId(), mapping);
   }

   void addMapping(State var1, int var2, PacketMapping var3);

   static PacketMappings arrayMappings() {
      return new PacketArrayMappings();
   }
}
