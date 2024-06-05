package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.aWC;

class Protocol1_13To1_12_2$33$1 extends ValueTransformer {
   final aWC d;

   Protocol1_13To1_12_2$33$1(aWC var1, Type var2) {
      super(var2);
      this.d = var1;
   }

   public Byte a(PacketWrapperImpl var1, Integer var2) throws Exception {
      return Byte.valueOf((byte)(var2.intValue() + 1));
   }
}
