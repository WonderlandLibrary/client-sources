package net;

import net.aRC;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.rewriters.RecipeRewriter;
import viaversion.viaversion.api.type.Type;

class ao2 extends acE {
   final RecipeRewriter d;
   final aRC c;

   ao2(aRC var1, RecipeRewriter var2) {
      this.c = var1;
      this.d = var2;
   }

   public void registerMap() {
      this.a(ao2::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(RecipeRewriter var0, PacketWrapper var1) throws Exception {
      aRC.b();
      int var3 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      int var4 = 0;
      int var5 = 0;
      if(var5 < var3) {
         String var6 = (String)var1.read(Type.STRING);
         String var7 = var6.replace("minecraft:", "");
         String var8 = (String)var1.read(Type.STRING);
         if(var7.equals("crafting_special_repairitem")) {
            ++var4;
         }

         var1.write(Type.STRING, var6);
         var1.write(Type.STRING, var8);
         var0.handle(var1, var7);
         ++var5;
      }

      var1.set(Type.VAR_INT, 0, Integer.valueOf(var3 - var4));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
