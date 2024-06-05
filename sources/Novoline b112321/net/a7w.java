package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aQU;

class a7w extends PacketRemapper {
   final aQU c;

   a7w(aQU var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      int var2 = ((Integer)var1.b((Type)Type.VAR_INT)).intValue();
      var1.a(Type.VAR_INT, Integer.valueOf(aQU.a((aQU)this.c).d().getNewItemId(var2)));
   }
}
