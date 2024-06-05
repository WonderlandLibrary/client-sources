package net;

import net.a8b;
import net.aaM;
import net.acE;
import viaversion.viaversion.api.type.Type;

final class a7g extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(new a8b(this));
      this.a(Type.STRING);
      this.a(Type.POSITION);
      this.a(Type.BYTE, Type.UNSIGNED_BYTE);
      this.a(new aaM(this));
   }
}
