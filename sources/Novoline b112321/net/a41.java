package net;

import net.aKr;
import net.aRL;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class a41 implements PacketHandler {
   final aKr a;

   a41(aKr var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aRL.a();
      var1.passthrough(Type.BOOLEAN);
      int var3 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      int var4 = 0;
      if(var4 < var3) {
         var1.passthrough(Type.STRING);
         if(((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue()) {
            var1.passthrough(Type.STRING);
         }

         if(((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue()) {
            var1.passthrough(Type.COMPONENT);
            var1.passthrough(Type.COMPONENT);
            Item var5 = (Item)var1.read(Type.FLAT_ITEM);
            var1.write(Type.FLAT_VAR_INT_ITEM, var5);
            var1.passthrough(Type.VAR_INT);
            int var6 = ((Integer)var1.passthrough(Type.INT)).intValue();
            if((var6 & 1) != 0) {
               var1.passthrough(Type.STRING);
            }

            var1.passthrough(Type.FLOAT);
            var1.passthrough(Type.FLOAT);
         }

         var1.passthrough(Type.STRING_ARRAY);
         int var8 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
         int var9 = 0;
         if(var9 < var8) {
            var1.passthrough(Type.STRING_ARRAY);
            ++var9;
         }

         ++var4;
      }

      if(acE.b() == null) {
         aRL.b("lz9fOb");
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
