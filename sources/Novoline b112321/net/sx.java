package net;

import java.util.Optional;
import net.a_O;
import net.ane;
import net.aq1;
import net.dr;
import viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class sx implements PacketHandler {
   final ane a;

   sx(ane var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aq1.a();
      Optional var3 = a_O.a(((Byte)var1.get(Type.BYTE, 0)).byteValue());
      if(var3.isPresent() && var3.get() == a_O.FALLING_BLOCK) {
         int var4 = ((Integer)var1.get(Type.INT, 0)).intValue();
         int var5 = var4 & 4095;
         int var6 = var4 >> 12 & 15;
         dr var7 = ((Protocol1_9_4To1_10)this.a.c.c()).c().a(var5, var6);
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
