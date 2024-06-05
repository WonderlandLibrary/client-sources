package net;

import net.acE;
import net.aqX;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.MapColorRewriter;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class anf extends acE {
   final aqX c;

   anf(aqX var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.BYTE);
      this.a(Type.BOOLEAN);
      this.a(Type.BOOLEAN);
      this.a(anf::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      int var2 = ((Integer)var0.passthrough(Type.VAR_INT)).intValue();
      aqX.a();
      int var3 = 0;
      if(var3 < var2) {
         var0.passthrough(Type.VAR_INT);
         var0.passthrough(Type.BYTE);
         var0.passthrough(Type.BYTE);
         var0.passthrough(Type.BYTE);
         if(((Boolean)var0.passthrough(Type.BOOLEAN)).booleanValue()) {
            var0.passthrough(Type.COMPONENT);
         }

         ++var3;
      }

      var3 = ((Short)var0.passthrough(Type.UNSIGNED_BYTE)).shortValue();
      if(var3 >= 1) {
         var0.passthrough(Type.UNSIGNED_BYTE);
         var0.passthrough(Type.UNSIGNED_BYTE);
         var0.passthrough(Type.UNSIGNED_BYTE);
         byte[] var4 = (byte[])var0.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
         int var5 = 0;
         if(var5 < var4.length) {
            int var6 = var4[var5] & 255;
            int var7 = MapColorRewriter.getMappedColor(var6);
            if(var7 != -1) {
               var4[var5] = (byte)var7;
            }

            ++var5;
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
