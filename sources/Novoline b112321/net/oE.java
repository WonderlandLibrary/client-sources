package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import net.EN;
import net.aMo;
import net.aMz;
import net.aXe;

class oE implements EN {
   final aMo a;

   oE(aMo var1) {
      this.a = var1;
   }

   public void a(PacketWrapperImpl var1) throws Exception {
      aXe.b();
      aMz[] var3 = (aMz[])var1.b(Type.ITEM_ARRAY, 0);
      int var5 = var3.length;
      int var6 = 0;
      if(var6 < var5) {
         aMz var7 = var3[var6];
         ItemRewriter.c(var7);
         ++var6;
      }

   }
}
