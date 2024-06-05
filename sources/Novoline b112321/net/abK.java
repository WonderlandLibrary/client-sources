package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.Zv;
import net.aM6;
import net.aMz;
import net.aRY;
import net.bgR;

class abK implements Zv {
   final aM6 a;

   abK(aM6 var1) {
      this.a = var1;
   }

   public void b(PacketWrapperImpl var1) throws Exception {
      aMz var2 = aRY.a((bgR)var1.e());
      var1.a(Type.ITEM, var2);
   }
}
