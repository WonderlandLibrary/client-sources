package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;

public enum ServerboundLoginPackets implements ServerboundPacketType {
   HELLO,
   ENCRYPTION_KEY,
   CUSTOM_QUERY_ANSWER,
   LOGIN_ACKNOWLEDGED;

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
      return State.LOGIN;
   }
}
