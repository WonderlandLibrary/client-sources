package com.viaversion.viaversion.api.protocol.packet;

public interface ServerboundPacketType extends PacketType {
   @Override
   default Direction direction() {
      return Direction.SERVERBOUND;
   }
}
