package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_9_4To1_10$4 extends PacketRemapper {
   final Protocol1_9_4To1_10 this$0;

   Protocol1_9_4To1_10$4(Protocol1_9_4To1_10 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.map(Type.STRING, Type.af);
      this.a(Type.VAR_INT);
   }
}
