package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;

final class EntityPackets$1 extends ValueTransformer {
   EntityPackets$1(Type var1) {
      super(var1);
   }

   public Short a(PacketWrapperImpl var1, Byte var2) {
      return Short.valueOf((short)(var2.byteValue() * 128));
   }
}
