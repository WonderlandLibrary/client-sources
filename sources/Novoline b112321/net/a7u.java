package net;

import com.google.gson.JsonElement;
import net.acE;
import net.km;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.api.type.Type;

class a7u extends acE {
   final km c;

   a7u(km var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      MetadataRewriter.e();
      int var3 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      if(var3 >= 0 && var3 <= 2) {
         this.c.processText((JsonElement)var1.passthrough(Type.COMPONENT));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
