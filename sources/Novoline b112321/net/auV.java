package net;

import net.BS;
import net.RL;
import net.aWW;
import net.a_4;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.EntityTracker1_13;

class auV implements PacketHandler {
   final aWW a;

   auV(aWW var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var3 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      BS.b();
      byte var4 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
      a_4 var5 = RL.a(var4, true);
      if(var5 != null) {
         if(var5.is(a_4.FALLING_BLOCK)) {
            int var6 = ((Integer)var1.get(Type.INT, 0)).intValue();
            int var7 = (var6 & 4095) << 4 | var6 >> 12 & 15;
            var1.set(Type.INT, 0, Integer.valueOf(BS.a(var7)));
         }

         if(var5.is(a_4.ITEM_FRAME)) {
            int var8 = ((Integer)var1.get(Type.INT, 0)).intValue();
            switch(var8) {
            case 0:
               var8 = 3;
            case 1:
               var8 = 4;
            case 3:
               var8 = 5;
            case 2:
            default:
               var1.set(Type.INT, 0, Integer.valueOf(var8));
               ((EntityTracker1_13)var1.user().b(EntityTracker1_13.class)).addEntity(var3, var5);
            }
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
