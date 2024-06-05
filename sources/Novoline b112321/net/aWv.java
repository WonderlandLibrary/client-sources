package net;

import net.BS;
import net.Mc;
import net.acE;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.type.Type;

final class aWv extends acE {
   public void registerMap() {
      acE[] var1 = BS.b();
      if(Via.getConfig().isServersideBlockConnections()) {
         this.a(Type.FLOAT);
         this.a(Type.FLOAT);
         this.a(Type.FLOAT);
         this.a(Type.FLOAT);
         this.a(Type.INT);
         this.a(new Mc(this));
      }
   }
}
