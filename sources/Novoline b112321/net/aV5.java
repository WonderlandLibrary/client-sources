package net;

import net.E4;
import net.ZK;
import net.acE;
import net.amk;
import viaversion.viaversion.api.type.Type;

final class aV5 extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.SHORT, new ZK(this, Type.VAR_INT));
      this.a(Type.ITEM);
      this.a(new E4(this));
      this.a(new amk(this));
   }
}
