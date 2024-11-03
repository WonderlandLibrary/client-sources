package com.viaversion.viaversion.api.protocol.packet.provider;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

final class PacketTypeArrayMap<P extends PacketType> implements PacketTypeMap<P> {
   private final Map<String, P> packetsByName;
   private final P[] packets;

   PacketTypeArrayMap(Map<String, P> packetsByName, P[] packets) {
      this.packetsByName = packetsByName;
      this.packets = packets;
   }

   @Nullable
   @Override
   public P typeByName(String packetTypeName) {
      return this.packetsByName.get(packetTypeName);
   }

   @Nullable
   @Override
   public P typeById(int packetTypeId) {
      return packetTypeId >= 0 && packetTypeId < this.packets.length ? this.packets[packetTypeId] : null;
   }

   @Override
   public Collection<P> types() {
      return Arrays.asList(this.packets);
   }
}
