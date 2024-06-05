package net;

import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import net.EN;
import net.aMz;
import net.anx;
import net.axZ;

class jp implements EN {
   final anx a;

   jp(anx var1) {
      this.a = var1;
   }

   public void a(PacketWrapperImpl var1) throws Exception {
      aMz var2 = (aMz)var1.b(axZ.c, 0);
      ItemRewriter.a(var2);
      var1.a(axZ.c, 0, var2);
   }
}
