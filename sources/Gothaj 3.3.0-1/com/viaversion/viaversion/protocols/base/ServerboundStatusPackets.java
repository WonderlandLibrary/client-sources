package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;

public enum ServerboundStatusPackets implements ServerboundPacketType {
   STATUS_REQUEST,
   PING_REQUEST;

   @Override
   public final int getId() {
      return this.ordinal();
   }

   @Override
   public final String getName() {
      return this.name();
   }

   @Override
   public final State state() {
      return State.STATUS;
   }
}
