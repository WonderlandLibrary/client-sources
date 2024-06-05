package net;

import net.aM7;
import net.aRY;
import net.aXe;
import net.cR;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets$8$2$1;

class a0W implements PacketHandler {
   final aM7 a;

   a0W(aM7 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aXe.b();
      short var3 = ((Short)var1.get(Type.UNSIGNED_BYTE, 0)).shortValue();
      short var4 = ((Short)var1.get(Type.SHORT, 0)).shortValue();
      boolean var5 = var4 == 45 && var3 == 0;
      cR var6 = (cR)var1.user().b(cR.class);
      if(var6.a() != null && var6.a().equals("minecraft:brewing_stand")) {
         if(var4 == 4) {
            var5 = true;
         }

         if(var4 > 4) {
            var1.set(Type.SHORT, 0, Short.valueOf((short)(var4 - 1)));
         }
      }

      if(var5) {
         var1.create(22, new InventoryPackets$8$2$1(this, var3, var4)).send(aRY.class);
         var1.set(Type.BYTE, 0, Byte.valueOf((byte)0));
         var1.set(Type.BYTE, 1, Byte.valueOf((byte)0));
         var1.set(Type.SHORT, 0, Short.valueOf((short)-999));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
