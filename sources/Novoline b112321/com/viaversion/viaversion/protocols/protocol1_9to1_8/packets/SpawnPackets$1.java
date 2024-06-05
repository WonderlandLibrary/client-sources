package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;

final class SpawnPackets$1 extends ValueTransformer {
   SpawnPackets$1(Type var1) {
      super(var1);
   }

   public Double a(PacketWrapperImpl var1, Integer var2) {
      return Double.valueOf((double)var2.intValue() / 32.0D);
   }
}
