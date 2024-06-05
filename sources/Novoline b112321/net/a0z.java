package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
import net.EN;
import net.a9C;
import net.amf;

class a0z implements EN {
   final a9C a;

   a0z(a9C var1) {
      this.a = var1;
   }

   public void a(PacketWrapperImpl var1) throws Exception {
      int var2 = ((Integer)var1.b((Type)Type.VAR_INT)).intValue();
      int var3 = var2 >> 4;
      int var4 = var2 & 15;
      amf var5 = ReplacementRegistry1_7_6_10to1_8.a(new amf(var3, var4));
      var3 = var5.c();
      var4 = var5.a();
      var1.a(Type.VAR_INT, Integer.valueOf(var3));
      var1.a(Type.M, Short.valueOf((short)var4));
   }
}
