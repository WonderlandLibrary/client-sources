package net;

import net.DD;
import net.QH;
import net.acE;
import viaversion.viaversion.api.type.Type;

final class aDn extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT, Type.INT);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(new DD(this));
      this.a(new QH(this));
   }
}
