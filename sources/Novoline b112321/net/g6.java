package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aou;
import net.aqb;
import net.z8;

class g6 implements EN {
   final aou a;

   g6(aou var1) {
      this.a = var1;
   }

   public void a(PacketWrapperImpl var1) throws Exception {
      double var2 = (double)((Short)var1.b(Type.SHORT, 0)).shortValue() / 4096.0D;
      double var4 = (double)((Short)var1.b(Type.SHORT, 1)).shortValue() / 4096.0D;
      double var6 = (double)((Short)var1.b(Type.SHORT, 2)).shortValue() / 4096.0D;
      z8.a(aqb.a(this.a.c), var1, var2, var4, var6, false, true);
   }
}
