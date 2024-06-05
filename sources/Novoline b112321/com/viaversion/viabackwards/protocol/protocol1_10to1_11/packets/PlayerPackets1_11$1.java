package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;

final class PlayerPackets1_11$1 extends ValueTransformer {
   PlayerPackets1_11$1(Type var1) {
      super(var1);
   }

   public Float a(PacketWrapperImpl var1, Short var2) throws Exception {
      return Float.valueOf((float)var2.shortValue() / 15.0F);
   }
}
