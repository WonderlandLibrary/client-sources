package com.viaversion.viaversion.api.protocol.packet;

public interface ClientboundPacketType extends PacketType {
   @Override
   default Direction direction() {
      return Direction.CLIENTBOUND;
   }
}
