package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2;

import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;

final class Protocol1_9_3To1_9_1_2$1 extends ValueTransformer {
   Protocol1_9_3To1_9_1_2$1(Type var1, Type var2) {
      super(var1, var2);
   }

   public Short a(PacketWrapperImpl var1, Short var2) throws Exception {
      return Short.valueOf((short)Math.round((float)var2.shortValue() / 63.5F * 63.0F));
   }
}
