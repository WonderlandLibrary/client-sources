package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import net.aAX;

class Protocol1_9_4To1_10$3 extends PacketRemapper {
   final aAX d;
   final Protocol1_9_4To1_10 this$0;

   Protocol1_9_4To1_10$3(Protocol1_9_4To1_10 var1, aAX var2) {
      this.this$0 = var1;
      this.d = var2;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.VAR_INT);
      this.a(Type.I);
      this.a(Type.I);
      this.a(Type.I);
      this.a(Type.FLOAT);
      this.map(Type.FLOAT, Protocol1_9_4To1_10.access$000());
      this.a(this.d.a());
   }
}
