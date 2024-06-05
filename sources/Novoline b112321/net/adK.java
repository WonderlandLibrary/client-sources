package net;

import net.aK6;
import net.q1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;

class adK implements PacketHandler {
   final aK6 a;

   adK(aK6 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      q1.b();
      int var3 = ((Integer)var1.read(Type.VAR_INT)).intValue();
      int var4 = ((Integer)var1.read(Type.VAR_INT)).intValue();
      var1.cancel();
      if(var3 == 383) {
         int var5 = 0;
         if(var5 < 44) {
            Integer var6 = Integer.valueOf(this.a.c.getMappingData().getItemMappings().get(var3 << 16 | var5));
            PacketWrapper var7 = var1.create(24);
            var7.write(Type.VAR_INT, var6);
            var7.write(Type.VAR_INT, Integer.valueOf(var4));
            var7.send(Protocol1_13To1_12_2.class);
            ++var5;
         }
      }

      int var9 = 0;
      if(var9 < 16) {
         int var11 = this.a.c.getMappingData().getItemMappings().get(var3 << 4 | var9);
         if(var11 != -1) {
            PacketWrapper var12 = var1.create(24);
            var12.write(Type.VAR_INT, Integer.valueOf(var11));
            var12.write(Type.VAR_INT, Integer.valueOf(var4));
            var12.send(Protocol1_13To1_12_2.class);
            ++var9;
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
