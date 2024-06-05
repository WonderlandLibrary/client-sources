package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import net.aAX;
import net.rL;

class Protocol1_9_4To1_10$2 extends PacketRemapper {
   final aAX c;
   final Protocol1_9_4To1_10 this$0;

   Protocol1_9_4To1_10$2(Protocol1_9_4To1_10 var1, aAX var2) {
      this.this$0 = var1;
      this.c = var2;
   }

   public void registerMap() {
      rL.b();
      this.a(Type.STRING);
      this.a(Type.VAR_INT);
      this.a(Type.I);
      this.a(Type.I);
      this.a(Type.I);
      this.a(Type.FLOAT);
      this.map(Type.FLOAT, Protocol1_9_4To1_10.access$000());
      this.a(this.c.c());
      if(PacketRemapper.b() == null) {
         rL.b(false);
      }

   }
}
