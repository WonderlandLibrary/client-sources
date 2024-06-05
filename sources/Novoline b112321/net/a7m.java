package net;

import com.google.gson.JsonElement;
import net.acE;
import net.km;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.api.type.Type;

class a7m extends acE {
   final km c;

   a7m(km var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      boolean var2 = MetadataRewriter.c();
      if(((Integer)var1.passthrough(Type.VAR_INT)).intValue() == 2) {
         var1.passthrough(Type.VAR_INT);
         var1.passthrough(Type.INT);
         this.c.processText((JsonElement)var1.passthrough(Type.COMPONENT));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
