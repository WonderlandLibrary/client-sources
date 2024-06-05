package net;

import net.aca;
import net.aqw;
import net.ay_;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class iF implements PacketHandler {
   final aca a;

   iF(aca var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      String var2 = aqw.a();
      if(((Integer)var1.get(Type.VAR_INT, 0)).intValue() == 1) {
         var1.set(Type.ITEM, 0, (Object)null);
         PacketWrapper var4 = var1.create(6);
         var4.write(Type.BYTE, Byte.valueOf(((Short)var1.get(Type.UNSIGNED_BYTE, 0)).byteValue()));
         var4.write(Type.SHORT, var1.get(Type.SHORT, 1));
         var4.write(Type.BOOLEAN, Boolean.valueOf(false));
         var1.sendToServer(ay_.class, true, true);
         var1.cancel();
         var4.sendToServer(ay_.class, true, true);
      } else {
         Item var3 = (Item)var1.get(Type.ITEM, 0);
         this.a.c.c(var3);
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
