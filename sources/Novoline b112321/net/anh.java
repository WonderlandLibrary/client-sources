package net;

import net.aR8;
import net.acE;
import viaversion.viafabric.protocol.ViaFabricHostnameProtocol$1$1;
import viaversion.viaversion.api.type.Type;

class anh extends acE {
   final aR8 c;

   anh(aR8 var1) {
      this.c = var1;
   }

   public void registerMap() {
      aR8.b();
      this.a(Type.VAR_INT);
      this.a(Type.STRING, new ViaFabricHostnameProtocol$1$1(this, Type.STRING));
   }
}
