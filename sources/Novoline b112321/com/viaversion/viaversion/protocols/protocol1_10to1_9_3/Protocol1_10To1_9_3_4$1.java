package com.viaversion.viaversion.protocols.protocol1_10to1_9_3;

import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;

final class Protocol1_10To1_9_3_4$1 extends ValueTransformer {
   Protocol1_10To1_9_3_4$1(Type var1) {
      super(var1);
   }

   public Float a(PacketWrapperImpl var1, Short var2) throws Exception {
      return Float.valueOf((float)var2.shortValue() / 63.0F);
   }
}
