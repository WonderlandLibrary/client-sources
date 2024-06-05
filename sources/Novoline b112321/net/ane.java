package net;

import java.util.function.Function;
import net.Oz;
import net.acE;
import net.aq1;
import net.sx;
import viaversion.viaversion.api.entities.ObjectType;
import viaversion.viaversion.api.type.Type;

class ane extends acE {
   final aq1 c;

   ane(aq1 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.BYTE);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.INT);
      this.a(aq1.a(this.c));
      this.a(aq1.a(this.c, ane::lambda$registerMap$0));
      this.a(new sx(this));
   }

   private static ObjectType lambda$registerMap$0(Byte var0) {
      return (Oz)Oz.a(var0.byteValue()).orElse((Object)null);
   }
}
