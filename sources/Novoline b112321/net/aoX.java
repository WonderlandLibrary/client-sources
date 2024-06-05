package net;

import net.Q4;
import net.acE;
import viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import viaversion.viaversion.api.type.Type;

class aoX extends acE {
   final TranslatableRewriter d;
   final Protocol1_13To1_13_1 c;

   aoX(Protocol1_13To1_13_1 var1, TranslatableRewriter var2) {
      this.c = var1;
      this.d = var2;
   }

   public void registerMap() {
      this.a(Type.UUID);
      this.a(Type.VAR_INT);
      this.a(new Q4(this));
   }
}
