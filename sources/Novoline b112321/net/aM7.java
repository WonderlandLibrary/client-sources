package net;

import net.a0W;
import net.aZZ;
import net.acE;
import viaversion.viaversion.api.type.Type;

final class aM7 extends acE {
   public void registerMap() {
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.SHORT);
      this.a(Type.BYTE);
      this.a(Type.SHORT);
      this.a(Type.VAR_INT, Type.BYTE);
      this.a(Type.ITEM);
      this.a(new aZZ(this));
      this.a(new a0W(this));
   }
}
