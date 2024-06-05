package net;

import java.util.function.Function;
import net.Oz;
import net.aL2;
import net.acE;
import net.aqp;
import viaversion.viaversion.api.entities.ObjectType;
import viaversion.viaversion.api.type.Type;

class ac0 extends acE {
   final aqp c;

   ac0(aqp var1) {
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
      this.a(aqp.a(this.c));
      this.a(aqp.a(this.c, ac0::lambda$registerMap$0));
      this.a(new aL2(this));
   }

   private static ObjectType lambda$registerMap$0(Byte var0) {
      return (Oz)Oz.a(var0.byteValue()).orElse((Object)null);
   }
}
