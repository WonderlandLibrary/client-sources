package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import net.EN;
import net.aTD;
import net.aiL;
import net.g1;

class a7e extends PacketRemapper {
   final g1 c;

   a7e(g1 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$2);
   }

   private void lambda$registerMap$2(PacketWrapperImpl var1) throws Exception {
      aTD.c();
      aiL var3 = g1.e(this.c).d();
      g1.a(this.c, var1, a7e::lambda$null$0, g1.c(this.c));
      g1.a(this.c, var1, a7e::lambda$null$1, g1.b(this.c));
      if(g1.a(this.c) != null || !g1.d(this.c).isEmpty()) {
         int var4 = ((Integer)var1.a((Type)Type.VAR_INT)).intValue();
         int var5 = 0;
         if(var5 < var4) {
            var1.a(Type.STRING);
            var1.a(Type.VAR_INT_ARRAY_PRIMITIVE);
            ++var5;
         }

         g1.a(this.c, var1, g1.a(this.c), g1.d(this.c));
      }
   }

   private static int lambda$null$1(aiL var0, int var1) {
      boolean var2 = aTD.c();
      return (var0 != null?Integer.valueOf(var0.getNewItemId(var1)):null).intValue();
   }

   private static int lambda$null$0(aiL var0, int var1) {
      boolean var2 = aTD.c();
      return (var0 != null?Integer.valueOf(var0.getNewBlockId(var1)):null).intValue();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
