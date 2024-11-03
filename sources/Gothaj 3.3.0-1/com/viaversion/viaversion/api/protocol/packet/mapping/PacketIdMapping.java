package com.viaversion.viaversion.api.protocol.packet.mapping;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import org.checkerframework.checker.nullness.qual.Nullable;

final class PacketIdMapping implements PacketMapping {
   private final int mappedPacketId;
   private final PacketHandler handler;

   PacketIdMapping(int mappedPacketId, @Nullable PacketHandler handler) {
      this.mappedPacketId = mappedPacketId;
      this.handler = handler;
   }

   @Override
   public void applyType(PacketWrapper wrapper) {
      wrapper.setId(this.mappedPacketId);
   }

   @Nullable
   @Override
   public PacketHandler handler() {
      return this.handler;
   }
}
