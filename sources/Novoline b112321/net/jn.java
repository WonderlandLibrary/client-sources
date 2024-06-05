package net;

import java.util.Optional;
import net.aCp;
import net.aQj;
import net.aqI;
import net.ayk;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class jn implements PacketHandler {
   final aCp a;

   jn(aCp var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aqI.a();
      Optional var3 = aQj.b(((Byte)var1.get(Type.BYTE, 0)).byteValue());
      if(var3.isPresent()) {
         aQj var4 = (aQj)var3.get();
         if(var4 == aQj.FALLING_BLOCK) {
            int var5 = ((Integer)var1.get(Type.INT, 0)).intValue();
            int var6 = ayk.k.getNewBlockStateId(var5);
            var6 = var6 >> 4 & 4095 | (var6 & 15) << 12;
            var1.set(Type.INT, 0, Integer.valueOf(var6));
         }

         if(var4 == aQj.ITEM_FRAME) {
            int var7 = ((Integer)var1.get(Type.INT, 0)).intValue();
            switch(var7) {
            case 3:
               var7 = 0;
            case 4:
               var7 = 1;
            case 5:
               var7 = 3;
            default:
               var1.set(Type.INT, 0, Integer.valueOf(var7));
            }
         }

         if(var4 == aQj.TRIDENT) {
            var1.set(Type.BYTE, 0, Byte.valueOf((byte)aQj.TIPPED_ARROW.getId()));
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
