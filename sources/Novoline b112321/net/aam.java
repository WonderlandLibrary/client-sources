package net;

import net.RL;
import net.aOz;
import net.aQj;
import net.a_4;
import net.aoS;
import net.aq6;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aam implements PacketHandler {
   final aoS a;

   aam(aoS var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aq6.a();
      byte var3 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
      int var4 = this.a.c.getOldEntityId(var3);
      a_4 var5 = RL.a(var4, false);
      if(var5.isOrHasParent(a_4.MINECART_ABSTRACT)) {
         aQj var6 = aQj.MINECART;
         byte var7 = 0;
         switch(aOz.a[var5.ordinal()]) {
         case 1:
            var7 = 1;
         case 2:
            var7 = 2;
         case 3:
            var7 = 3;
         case 4:
            var7 = 4;
         case 5:
            var7 = 5;
         case 6:
            var7 = 6;
         default:
            if(var7 != 0) {
               var1.set(Type.INT, 0, Integer.valueOf(var7));
            }
         }
      }

      aQj var10 = (aQj)aQj.a(var5).orElse((Object)null);
      if(var10 != null) {
         var1.set(Type.BYTE, 0, Byte.valueOf((byte)var10.getId()));
         int var16 = ((Integer)var1.get(Type.INT, 0)).intValue();
         if(var10 == aQj.FALLING_BLOCK) {
            int var8 = ((Integer)var1.get(Type.INT, 0)).intValue();
            int var9 = ((Protocol1_13_2To1_14)EntityPackets1_14.b(this.a.c)).getMappingData().getNewBlockStateId(var8);
            var1.set(Type.INT, 0, Integer.valueOf(var9));
         }

         if(var5.isOrHasParent(a_4.ABSTRACT_ARROW)) {
            var1.set(Type.INT, 0, Integer.valueOf(var16 + 1));
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
