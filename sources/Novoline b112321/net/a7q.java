package net;

import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.api.rewriters.RecipeRewriter;
import viaversion.viaversion.api.type.Type;

class a7q extends acE {
   final RecipeRewriter c;

   a7q(RecipeRewriter var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      MetadataRewriter.e();
      int var3 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      int var4 = 0;
      if(var4 < var3) {
         String var5 = ((String)var1.passthrough(Type.STRING)).replace("minecraft:", "");
         String var6 = (String)var1.passthrough(Type.STRING);
         this.c.handle(var1, var5);
         ++var4;
      }

   }
}
