package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.SoundPackets1_12;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;

class SoundPackets1_12$1 extends PacketRemapper {
   final SoundPackets1_12 this$0;

   SoundPackets1_12$1(SoundPackets1_12 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(Type.STRING);
      this.a(Type.VAR_INT);
      this.a(Type.I);
      this.a(Type.I);
      this.a(Type.I);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
   }
}
