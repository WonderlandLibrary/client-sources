package net;

import net.a1R;
import net.aK1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class bgr implements PacketHandler {
   final aK1 a;

   bgr(aK1 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      a1R.b();
      int var3 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      int var4 = 0;
      if(var4 < var3) {
         var1.passthrough(Type.STRING);
         String var5 = (String)var1.passthrough(Type.STRING);
         if(var5.equals("crafting_shapeless")) {
            var1.passthrough(Type.STRING);
            int var6 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
            int var7 = 0;
            if(var7 < var6) {
               var1.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, var1.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
               ++var7;
            }

            var1.write(Type.FLAT_VAR_INT_ITEM, var1.read(Type.FLAT_ITEM));
         }

         if(var5.equals("crafting_shaped")) {
            int var9 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue() * ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
            var1.passthrough(Type.STRING);
            int var11 = 0;
            if(var11 < var9) {
               var1.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, var1.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
               ++var11;
            }

            var1.write(Type.FLAT_VAR_INT_ITEM, var1.read(Type.FLAT_ITEM));
         }

         if(var5.equals("smelting")) {
            var1.passthrough(Type.STRING);
            var1.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, var1.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
            var1.write(Type.FLAT_VAR_INT_ITEM, var1.read(Type.FLAT_ITEM));
            var1.passthrough(Type.FLOAT);
            var1.passthrough(Type.VAR_INT);
         }

         ++var4;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
