package net;

import net.LV;
import net.acE;
import net.ar3;
import net.asG;
import viaversion.viaversion.api.type.Type;

final class a9v extends acE {
   public void registerMap() {
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.STRING);
      this.a(Type.COMPONENT);
      this.a(Type.UNSIGNED_BYTE);
      this.a(new asG(this));
      this.a(new ar3(this));
      this.a(new LV(this));
   }
}
