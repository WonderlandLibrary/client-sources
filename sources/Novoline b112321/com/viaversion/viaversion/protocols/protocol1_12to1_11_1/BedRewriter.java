package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.aMz;
import net.aRp;

public class BedRewriter {
   public static void b(aMz var0) {
      PacketRemapper[] var1 = aRp.a();
      if(var0 != null) {
         if(var0.e() == 355 && var0.c() == 0) {
            var0.a((short)14);
         }

      }
   }

   public static void a(aMz var0) {
      PacketRemapper[] var1 = aRp.a();
      if(var0 != null) {
         if(var0.e() == 355 && var0.c() == 14) {
            var0.a((short)0);
         }

      }
   }
}
