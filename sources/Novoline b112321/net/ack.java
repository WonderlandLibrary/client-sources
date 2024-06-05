package net;

import net.acE;
import net.aq0;
import net.atx;
import viaversion.viaversion.api.type.Type;

class ack extends acE {
   final aq0 c;

   ack(aq0 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.POSITION);
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.NBT);
      this.a(new atx(this));
   }
}
