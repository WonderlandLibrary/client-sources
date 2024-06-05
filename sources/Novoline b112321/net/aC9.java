package net;

import java.util.function.Function;
import net.Oz;
import net.acE;
import net.aqL;
import viaversion.viaversion.api.entities.ObjectType;
import viaversion.viaversion.api.type.Type;

class aC9 extends acE {
   final aqL c;

   aC9(aqL var1) {
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
      this.a(aqL.a(this.c));
      this.a(aqL.a(this.c, aC9::lambda$registerMap$0));
   }

   private static ObjectType lambda$registerMap$0(Byte var0) {
      return (Oz)Oz.a(var0.byteValue()).orElse((Object)null);
   }
}
