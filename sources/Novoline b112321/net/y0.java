package net;

import net.aSG;
import net.aoJ;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class y0 implements PacketHandler {
   final aoJ a;

   y0(aoJ var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var3 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      aSG.b();
      int var4 = 0;
      if(var4 < var3) {
         var1.passthrough(Type.STRING);
         int[] var5 = (int[])var1.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
         int var6 = 0;
         if(var6 < var5.length) {
            int var7 = var5[var6];
            int var8 = this.a.c.getMappingData().getNewBlockId(var7);
            var5[var6] = var8;
            ++var6;
         }

         ++var4;
      }

      var4 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      int var12 = 0;
      if(var12 < var4) {
         var1.passthrough(Type.STRING);
         int[] var16 = (int[])var1.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
         int var20 = 0;
         if(var20 < var16.length) {
            int var24 = var16[var20];
            int var9 = this.a.c.getMappingData().getItemMappings().get(var24);
            var16[var20] = var9;
            ++var20;
         }

         ++var12;
      }

      var12 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      int var17 = 0;
      if(var17 < var12) {
         var1.passthrough(Type.STRING);
         var1.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
         ++var17;
      }

      var17 = ((Integer)var1.read(Type.VAR_INT)).intValue();
      int var22 = 0;
      if(var22 < var17) {
         var1.read(Type.STRING);
         var1.read(Type.VAR_INT_ARRAY_PRIMITIVE);
         ++var22;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
