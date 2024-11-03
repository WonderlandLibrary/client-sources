package com.viaversion.viaversion.api.protocol.packet.mapping;

import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.Nullable;

final class PacketArrayMappings implements PacketMappings {
   private final PacketMapping[][] packets = new PacketMapping[State.values().length][];

   @Nullable
   @Override
   public PacketMapping mappedPacket(State state, int unmappedId) {
      PacketMapping[] packets = this.packets[state.ordinal()];
      return packets != null && unmappedId >= 0 && unmappedId < packets.length ? packets[unmappedId] : null;
   }

   @Override
   public void addMapping(State state, int unmappedId, PacketMapping mapping) {
      int ordinal = state.ordinal();
      PacketMapping[] packets = this.packets[ordinal];
      if (packets == null) {
         packets = new PacketMapping[unmappedId + 8];
         this.packets[ordinal] = packets;
      } else if (unmappedId >= packets.length) {
         packets = Arrays.copyOf(packets, unmappedId + 32);
         this.packets[ordinal] = packets;
      }

      packets[unmappedId] = mapping;
   }
}
